package com.csp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/verify-pin")
public class VerifyPinServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("\n===== TRANSACTION PROCESSING STARTED =====");

        // 1. Validate session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountNumber") == null) {
            System.out.println("ERROR: Session expired or invalid.");
            response.sendRedirect("domestic.html?error=session_expired");
            return;
        }

        String accountNumber = (String) session.getAttribute("accountNumber");
        String pin = request.getParameter("pin");
        String amountStr = (String) session.getAttribute("amount"); // Fetch from session
        String type = (String) session.getAttribute("type");       // Fetch from session

        System.out.println("DEBUG: Params - Account:" + accountNumber 
            + " | PIN:" + pin 
            + " | Amount:" + amountStr 
            + " | Type:" + type);

        // 2. Validate input parameters
        if (pin == null || pin.isEmpty()) {
            System.out.println("ERROR: Missing PIN.");
            response.sendRedirect("enterpin.html?error=missing_pin");
            return;
        }

        // **Fix: If 'type' is null, assume it's a balance enquiry**
        if (type == null) {
            type = "balance";
            session.setAttribute("type", "balance"); // Ensure it's stored for consistency
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            System.out.println("DEBUG: DB Connection - SUCCESS");

            // 3. Verify PIN
            PreparedStatement checkStmt = conn.prepareStatement("SELECT pin FROM customers WHERE account_number = ?");
            checkStmt.setString(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("ERROR: Account not found.");
                response.sendRedirect("domestic.html?error=account_not_found");
                return;
            }

            String dbPin = rs.getString("pin");
            System.out.println("DEBUG: DB PIN - " + dbPin);

            if (!dbPin.equals(pin)) {
                System.out.println("ERROR: Incorrect PIN.");
                response.sendRedirect("enterpin.html?error=invalid_pin");
                return;
            }

            // 4. Process Balance Enquiry
            if (type.equalsIgnoreCase("balance")) {
                System.out.println("DEBUG: Redirecting to BalanceEnquiryServlet...");
                response.sendRedirect("balance-enquiry");
                return;
            }

            // 5. Validate Amount for Deposit/Withdrawal
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    System.out.println("ERROR: Invalid transaction amount.");
                    response.sendRedirect("enterpin.html?error=invalid_amount");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Amount format error - " + amountStr);
                response.sendRedirect("enterpin.html?error=invalid_amount_format");
                return;
            }

            // 6. Process Deposit or Withdrawal
            String sql;
            if (type.equalsIgnoreCase("deposit")) {
                sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            } else if (type.equalsIgnoreCase("withdrawal")) {
                sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
            } else {
                System.out.println("ERROR: Invalid transaction type.");
                response.sendRedirect("enterpin.html?error=invalid_transaction_type");
                return;
            }

            PreparedStatement updateStmt = conn.prepareStatement(sql);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountNumber);
            if (type.equalsIgnoreCase("withdrawal")) {
                updateStmt.setDouble(3, amount);
            }

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("ERROR: Transaction failed (insufficient funds?).");
                response.sendRedirect("enterpin.html?error=transaction_failed");
                return;
            }

            System.out.println("DEBUG: Balance updated successfully.");

            // 7. Log transaction
            PreparedStatement logStmt = conn.prepareStatement(
                "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)");
            logStmt.setString(1, accountNumber);
            logStmt.setString(2, type);
            logStmt.setDouble(3, amount);
            logStmt.executeUpdate();

            System.out.println("DEBUG: Transaction logged successfully.");

            // Clear session attributes after successful transaction
            session.removeAttribute("amount");
            session.removeAttribute("type");
            session.removeAttribute("accountNumber");

            response.sendRedirect("thankyou.html");

        } catch (SQLException e) {
            System.out.println("DATABASE ERROR:");
            e.printStackTrace();
            response.sendRedirect("error.html");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

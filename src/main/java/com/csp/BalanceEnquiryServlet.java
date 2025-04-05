package com.csp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/balance-enquiry")
public class BalanceEnquiryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Validate session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountNumber") == null) {
            System.out.println("ERROR: Session expired.");
            response.sendRedirect("domestic.html?error=session_expired");
            return;
        }

        String accountNumber = (String) session.getAttribute("accountNumber");
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            System.out.println("DEBUG: DB Connection - SUCCESS");

            // Fetch balance from database
            PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("DEBUG: Balance retrieved - ₹" + balance);

                // Redirect to displaybalance.html with balance value
                response.sendRedirect("displaybalance.html?balance=" + balance);
            } else {
                System.out.println("ERROR: Account not found.");
                response.sendRedirect("balance_enquiry.html?error=account_not_found");
            }

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

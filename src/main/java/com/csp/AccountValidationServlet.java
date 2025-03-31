package com.csp;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/validate-account")
public class AccountValidationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Get and validate account number
        String accountNumber = request.getParameter("accountNumber");
        System.out.println("RAW accountNumber param: '" + accountNumber + "'");
        
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            response.sendRedirect("domestic.html?error=3");
            return;
        }
        accountNumber = accountNumber.trim();

        // 2. Database validation
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT account_number, full_name FROM customers WHERE account_number = ?";
            System.out.println("Executing: " + sql.replace("?", "'" + accountNumber + "'"));
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // 3. Create authenticated session
                HttpSession session = request.getSession();
                session.setAttribute("accountNumber", accountNumber);
                session.setAttribute("customerName", rs.getString("full_name"));
                session.setMaxInactiveInterval(300); // 5-minute timeout
                
                System.out.println("SESSION CREATED: " + session.getId());
                System.out.println("Customer: " + rs.getString("full_name"));
                
                response.sendRedirect("transactions.html");
            } else {
                System.out.println("NO MATCH for: " + accountNumber);
                response.sendRedirect("domestic.html?error=1");
            }
        } catch (Exception e) {
            System.out.println("DATABASE ERROR:");
            e.printStackTrace();
            response.sendRedirect("domestic.html?error=2");
        }
    }
}
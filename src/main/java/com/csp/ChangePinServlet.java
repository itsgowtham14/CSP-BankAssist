package com.csp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ChangePinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("accountNumber");
        String oldPin = request.getParameter("oldPin");
        String newPin = request.getParameter("newPin");
        String confirmPin = request.getParameter("confirmPin");

        if (!newPin.equals(confirmPin)) {
            response.sendRedirect("pin.html?error=confirm_mismatch");
            return;
        }

        Connection conn = null;
        PreparedStatement psCheck = null, psUpdate = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/csp_management", "root", "root");

            psCheck = conn.prepareStatement("SELECT * FROM customers WHERE account_number = ? AND pin = ?");
            psCheck.setString(1, accountNumber);
            psCheck.setString(2, oldPin);
            rs = psCheck.executeQuery();

            if (rs.next()) {
                psUpdate = conn.prepareStatement("UPDATE customers SET pin = ? WHERE account_number = ?");
                psUpdate.setString(1, newPin);
                psUpdate.setString(2, accountNumber);

                int updated = psUpdate.executeUpdate();
                if (updated > 0) {
                    response.sendRedirect("thankyou.html");
                } else {
                    response.sendRedirect("pin.html?error=update_failed");
                }
            } else {
                response.sendRedirect("pin.html?error=invalid_old_pin");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pin.html?error=server_error");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (psCheck != null) psCheck.close(); } catch (Exception e) {}
            try { if (psUpdate != null) psUpdate.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}

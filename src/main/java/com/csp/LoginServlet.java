package com.csp;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Arrays;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    System.out.println("==== DEBUG START ===="); // Keep this for verification
	    
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    try (Connection conn = DBConnection.getConnection()) {
	        PreparedStatement stmt = conn.prepareStatement(
	            "SELECT * FROM users WHERE username=? AND password=?"
	        );
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            System.out.println("Login SUCCESS - Redirecting to home_bank.html");
	            response.sendRedirect("home_bank.html");  // Absolute path
	            return;  // ← THIS IS CRUCIAL!
	        } else {
	            System.out.println("Login FAILED - Wrong credentials");
	            response.sendRedirect("login.html?error=1");
	            return;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("login.html?error=2");
	        return;
	    }
	}
}
package com.csp;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DBConnection {
    static {
        try {
            // Explicitly load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/csp_management?useSSL=false",
            "root",      // Your MySQL username
            "root"   // Your MySQL password
        );
    }
}
package com.csp;

import java.sql.*;

public class DBConnection {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/csp_management?useSSL=false&allowPublicKeyRetrieval=true",
            "root",
            "root"
        );
    }
}

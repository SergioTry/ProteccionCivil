package com.dam.proteccioncivil.ui.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    public Connection conn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionString= "jdbc:mysql://34.175.113.180:3306/ProteccionCivil";
            String username = "admin";
            String password = "admin";
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            System.out.println("Error: "+e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: "+e);
        }
        return conn;
    }
}

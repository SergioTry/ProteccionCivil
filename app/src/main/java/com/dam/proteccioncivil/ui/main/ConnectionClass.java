package com.dam.proteccioncivil.ui.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    public Connection conn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String connectionString= "jdbc:mysql://34.175.113.180:3306/ProteccionCivil";
            String username = "root";
            String password = "";
            //establish a connection to a MySQL database using the JDBC driver
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            System.out.println("Error: "+e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: "+e);
        }
        return conn;
    }

}

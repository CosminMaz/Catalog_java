package org.example;

import java.sql.*;

public class OracleConnect {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE"; // sau @//localhost:1521/XE pentru service name
        String user = "catalog";
        String password = "user";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexiune reusita!");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

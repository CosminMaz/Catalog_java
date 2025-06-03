package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    // Updated connection details for Oracle XE
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "catalog";  // Usually uppercase for Oracle
    private static final String PASSWORD = "user";  // Usually uppercase for Oracle

    public static Connection getConnection() throws SQLException {
        try {
            // Make sure the Oracle driver is loaded
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Oracle JDBC Driver not found. Please check your dependencies.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            throw new SQLException("Oracle JDBC Driver not found", e);
        } catch (SQLException e) {
            String errorMessage = "Database connection error: " + e.getMessage() + 
                                "\nPlease check if:\n" +
                                "1. Oracle Database is running\n" +
                                "2. Username and password are correct\n" +
                                "3. Database service is available";
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Database Connection Error",
                JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public static boolean validateLogin(String username, String password, String userType) {
        String sql = userType.equals("student") 
            ? "SELECT * FROM studenti WHERE username = ? AND password = ?"
            : "SELECT * FROM profesori WHERE username = ? AND password = ?";
            
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error during login: " + e.getMessage(),
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean registerUser(String nume, String prenume, String email, String username, 
                                    String password, String userType, String grupa) {
        String sql;
        if (userType.equals("student")) {
            sql = "INSERT INTO studenti (id_student, nume, prenume, grupa, email, username, password) " +
                 "VALUES (seq_studenti.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO profesori (id_profesor, nume, prenume, email, username, password) " +
                 "VALUES (seq_profesori.NEXTVAL, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nume);
            pstmt.setString(2, prenume);
            if (userType.equals("student")) {
                pstmt.setString(3, grupa);
                pstmt.setString(4, email);
                pstmt.setString(5, username);
                pstmt.setString(6, password);
            } else {
                pstmt.setString(3, email);
                pstmt.setString(4, username);
                pstmt.setString(5, password);
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            String errorMessage = "Error during registration: " + e.getMessage();
            if (e.getMessage().contains("unique")) {
                errorMessage = "Username or email already exists. Please choose different ones.";
            }
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean checkUsernameExists(String username) {
        String sql = "SELECT username FROM studenti WHERE username = ? " +
                    "UNION " +
                    "SELECT username FROM profesori WHERE username = ?";
                    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error checking username: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
} 
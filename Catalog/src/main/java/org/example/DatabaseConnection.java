package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    // Updated connection details for Oracle XE
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "catalog";
    private static final String PASSWORD = "user";

    public static Connection getConnection() throws SQLException {
        try {
            // Incarcam driver pt a fi siguri
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Oracle JDBC Driver not found",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            throw new SQLException("Oracle JDBC Driver not found", e);
        } catch (SQLException e) {
            String errorMessage = "Database connection error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Database Connection Error",
                JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public static boolean validateLogin(String username, String password, String userType) {
        String sql = userType.equals("student") 
            ? "SELECT id_student FROM studenti WHERE username = ? AND parola = ?"
            : "SELECT id_profesor FROM profesori WHERE username = ? AND parola = ?";
            
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            System.out.println("Attempting login with:");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("User Type: " + userType);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResult = rs.next();
                System.out.println("Login result: " + (hasResult ? "Success" : "Failed"));
                return hasResult;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error during login: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error during login: " + e.getMessage(),
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean registerUser(String username, String password, String userType) {
        String sql;
        if (userType.equals("student")) {
            sql = "INSERT INTO studenti (username, parola) VALUES (?, ?)";
        } else {
            sql = "INSERT INTO profesori (username, parola) VALUES (?, ?)";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            String errorMessage = "Error during registration: " + e.getMessage();
            if (e.getMessage().contains("unique")) {
                errorMessage = "Username already exists. Please choose a different one.";
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

    public static int getUserId(String username, String userType) {
        String sql = userType.equals("student") 
            ? "SELECT id_student FROM studenti WHERE username = ?"
            : "SELECT id_profesor FROM profesori WHERE username = ?";
            
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return id_student sau id_profesor
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error getting user ID: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    public static List<GradeInfo> getStudentGrades(int studentId) {
        List<GradeInfo> grades = new ArrayList<>();
        String sql = "SELECT n.valoare_nota, n.data_nota, m.nume_materie, p.username " +
                    "FROM nota n " +
                    "JOIN materii m ON n.id_materie = m.id_materie " +
                    "JOIN profesori p ON m.id_profesor = p.id_profesor " +
                    "WHERE n.id_student = ? " +
                    "ORDER BY n.data_nota DESC";
            
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GradeInfo grade = new GradeInfo(
                        rs.getDouble("valoare_nota"),
                        rs.getDate("data_nota"),
                        rs.getString("nume_materie"),
                        rs.getString("username")
                    );
                    grades.add(grade);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error getting grades: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return grades;
    }

    public static List<GradeInfo> getProfessorGrades(int professorId) {
        List<GradeInfo> grades = new ArrayList<>();
        String sql = "SELECT n.valoare_nota, n.data_nota, m.nume_materie, s.username as student_username " +
                    "FROM nota n " +
                    "JOIN materii m ON n.id_materie = m.id_materie " +
                    "JOIN studenti s ON n.id_student = s.id_student " +
                    "WHERE m.id_profesor = ? " +
                    "ORDER BY n.data_nota DESC";
            
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, professorId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GradeInfo grade = new GradeInfo(
                        rs.getDouble("valoare_nota"),
                        rs.getDate("data_nota"),
                        rs.getString("nume_materie"),
                        rs.getString("student_username")
                    );
                    grades.add(grade);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error getting grades: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return grades;
    }
} 
package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardUI extends JFrame {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public DashboardUI(String username, String userType) {
        setTitle("Dashboard - " + username);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header panel with welcome message and logout button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Welcome label
        JLabel welcome = new JLabel("Welcome, " + username + " (" + userType + ")!");
        welcome.setFont(new Font("Arial", Font.BOLD, 16));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(welcome, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = createLogoutButton();
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(Color.WHITE);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        if ("student".equals(userType)) {
            displayStudentDashboard(username, mainPanel);
        } else {
            displayProfessorDashboard(username, mainPanel);
        }

        add(mainPanel);
        setVisible(true);
    }

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69)); // Red color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                new LoginRegisterUI();
                dispose();
            }
        });
        
        return logoutButton;
    }

    private void displayStudentDashboard(String username, JPanel mainPanel) {
        int studentId = DatabaseConnection.getUserId(username, "student");
        if (studentId != -1) {
            List<GradeInfo> grades = DatabaseConnection.getStudentGrades(studentId);
            
            // Create table model
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            
            // Add columns
            model.addColumn("Subject");
            model.addColumn("Grade");
            model.addColumn("Date");
            model.addColumn("Professor");

            // Add rows
            for (GradeInfo grade : grades) {
                model.addRow(new Object[]{
                    grade.getSubject(),
                    grade.getGrade(),
                    dateFormat.format(grade.getDate()),
                    grade.getProfessor()
                });
            }

            // Create and configure table
            JTable gradesTable = createGradesTable(model);
            mainPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);

            // Add statistics panel if there are grades
            if (!grades.isEmpty()) {
                addStatisticsPanel(grades, mainPanel);
            }
        } else {
            showErrorLabel(mainPanel);
        }
    }

    private void displayProfessorDashboard(String username, JPanel mainPanel) {
        int professorId = DatabaseConnection.getUserId(username, "profesor");
        if (professorId != -1) {
            List<GradeInfo> grades = DatabaseConnection.getProfessorGrades(professorId);
            
            // Create table model
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            
            // Add columns
            model.addColumn("Student");
            model.addColumn("Subject");
            model.addColumn("Grade");
            model.addColumn("Date");

            // Add rows
            for (GradeInfo grade : grades) {
                model.addRow(new Object[]{
                    grade.getProfessor(), // In this case, getProfessor returns student username
                    grade.getSubject(),
                    grade.getGrade(),
                    dateFormat.format(grade.getDate())
                });
            }

            // Create and configure table
            JTable gradesTable = createGradesTable(model);
            mainPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);

            // Add statistics panel
            if (!grades.isEmpty()) {
                addProfessorStatisticsPanel(grades, mainPanel);
            }
        } else {
            showErrorLabel(mainPanel);
        }
    }

    private JTable createGradesTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        return table;
    }

    private void addStatisticsPanel(List<GradeInfo> grades, JPanel mainPanel) {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Calculate average
        double average = grades.stream()
            .mapToDouble(GradeInfo::getGrade)
            .average()
            .orElse(0.0);

        JLabel averageLabel = new JLabel(String.format("Average Grade: %.2f", average));
        averageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(averageLabel);

        mainPanel.add(statsPanel, BorderLayout.SOUTH);
    }

    private void addProfessorStatisticsPanel(List<GradeInfo> grades, JPanel mainPanel) {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Calculate statistics
        double average = grades.stream()
            .mapToDouble(GradeInfo::getGrade)
            .average()
            .orElse(0.0);

        long totalGrades = grades.size();

        JLabel statsLabel = new JLabel(String.format("Total Grades: %d | Average Grade: %.2f", 
            totalGrades, average));
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(statsLabel);

        mainPanel.add(statsPanel, BorderLayout.SOUTH);
    }

    private void showErrorLabel(JPanel mainPanel) {
        JLabel errorLabel = new JLabel("Could not retrieve data. Please try again later.");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(errorLabel, BorderLayout.CENTER);
    }
}

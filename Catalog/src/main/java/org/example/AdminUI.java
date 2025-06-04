package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminUI extends JFrame {
    private JTextField studentUsernameField;
    private JPasswordField studentPasswordField;
    private JTextField profUsernameField;
    private JPasswordField profPasswordField;
    private JTextField subjectNameField;
    private JTextField profForSubjectField;

    public AdminUI() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Student", createStudentPanel());
        tabbedPane.addTab("Add Professor", createProfessorPanel());
        tabbedPane.addTab("Add Subject", createSubjectPanel());

        // Add back to login button
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Login");
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            new LoginRegisterUI();
            dispose();
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        studentUsernameField = new JTextField(20);
        panel.add(studentUsernameField, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        studentPasswordField = new JPasswordField(20);
        panel.add(studentPasswordField, gbc);

        // Add button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Student");
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addStudent());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createProfessorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        profUsernameField = new JTextField(20);
        panel.add(profUsernameField, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        profPasswordField = new JPasswordField(20);
        panel.add(profPasswordField, gbc);

        // Add button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Professor");
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addProfessor());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createSubjectPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Subject name field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Subject Name:"), gbc);
        gbc.gridx = 1;
        subjectNameField = new JTextField(20);
        panel.add(subjectNameField, gbc);

        // Professor username field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Professor Username:"), gbc);
        gbc.gridx = 1;
        profForSubjectField = new JTextField(20);
        panel.add(profForSubjectField, gbc);

        // Add button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Subject");
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addSubject());
        panel.add(addButton, gbc);

        return panel;
    }

    private void addStudent() {
        String username = studentUsernameField.getText();
        String password = new String(studentPasswordField.getPassword());

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username and password are required",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call add_student(?, ?)}")) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.execute();

            JOptionPane.showMessageDialog(this,
                "Student added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            studentUsernameField.setText("");
            studentPasswordField.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error adding student: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProfessor() {
        String username = profUsernameField.getText();
        String password = new String(profPasswordField.getPassword());

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username and password are required",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call add_professor(?, ?)}")) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.execute();

            JOptionPane.showMessageDialog(this,
                "Professor added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            profUsernameField.setText("");
            profPasswordField.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error adding professor: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSubject() {
        String subjectName = subjectNameField.getText();
        String profUsername = profForSubjectField.getText();

        if (subjectName.trim().isEmpty() || profUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Subject name and professor username are required",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call add_subject(?, ?)}")) {
            
            stmt.setString(1, subjectName);
            stmt.setString(2, profUsername);
            stmt.execute();

            JOptionPane.showMessageDialog(this,
                "Subject added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            subjectNameField.setText("");
            profForSubjectField.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error adding subject: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 
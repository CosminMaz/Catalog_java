package org.example;

import javax.swing.*;
import java.awt.*;

public class LoginRegisterUI extends JFrame {
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JComboBox<String> userTypeBox;

    public LoginRegisterUI() {
        setTitle("Login / Register");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Login", createLoginPanel());
        tabbedPane.add("Register", createRegisterPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Add Admin Panel button
        JButton adminButton = new JButton("Admin Panel");
        adminButton.setBackground(new Color(108, 117, 125)); // Gray color
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        adminButton.addActionListener(e -> {
            new AdminUI();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(adminButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(245, 245, 245));
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(33, 150, 243));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginUsernameField = new JTextField(15);
        panel.add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        panel.add(loginPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1;
        userTypeBox = new JComboBox<>(new String[]{"student", "profesor"});
        panel.add(userTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Register", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(76, 175, 80));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        regUsernameField = new JTextField(15);
        panel.add(regUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        regPasswordField = new JPasswordField(15);
        panel.add(regPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> regUserTypeBox = new JComboBox<>(new String[]{"student", "profesor"});
        panel.add(regUserTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(76, 175, 80));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.addActionListener(e -> {
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            String userType = (String) regUserTypeBox.getSelectedItem();

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Username and password are required.", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseConnection.checkUsernameExists(username)) {
                JOptionPane.showMessageDialog(this, 
                    "Username already exists.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = DatabaseConnection.registerUser(username, password, userType);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Account created successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    // Clear the fields after successful registration
                    regUsernameField.setText("");
                    regPasswordField.setText("");
                }
            }
        });
        panel.add(registerBtn, gbc);

        return panel;
    }

    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());
        String userType = (String) userTypeBox.getSelectedItem();

        boolean valid = DatabaseConnection.validateLogin(username, password, userType);
        if (valid) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardUI(username, userType);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginRegisterUI::new);
    }
}

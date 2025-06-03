package org.example;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {
    public DashboardUI(String username, String userType) {
        setTitle("Dashboard");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Welcome, " + username + " (" + userType + ")!");
        welcome.setFont(new Font("Arial", Font.BOLD, 16));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcome, BorderLayout.CENTER);
        setVisible(true);
    }
}

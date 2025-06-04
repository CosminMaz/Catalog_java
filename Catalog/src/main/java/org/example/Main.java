package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application starting...");
        
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("Look and Feel set successfully");

            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC Driver loaded successfully");

            // Initialize database first
            System.out.println("Starting database initialization...");
            DatabaseInitializer.initializeDatabase();
            System.out.println("Database initialization completed");

            // Register cleanup on JVM shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Performing database cleanup...");
                DatabaseInitializer.cleanupDatabase();
            }));
            System.out.println("Shutdown hook registered");

            // Start the UI in the EDT
            System.out.println("Starting UI initialization...");
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Creating LoginRegisterUI...");
                        LoginRegisterUI frame = new LoginRegisterUI();
                        frame.setVisible(true);
                        System.out.println("LoginRegisterUI created and set visible");
                    } catch (Exception e) {
                        System.err.println("Error creating UI: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                            "Error creating UI: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            System.out.println("UI initialization queued in EDT");

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Oracle JDBC Driver not found: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.println("Initialization error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Initialization error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

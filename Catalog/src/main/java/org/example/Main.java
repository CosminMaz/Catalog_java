package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Start the LoginRegisterUI on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> new LoginRegisterUI());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Oracle JDBC Driver not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Look and Feel error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

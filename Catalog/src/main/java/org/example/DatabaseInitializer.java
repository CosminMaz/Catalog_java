package org.example;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DatabaseInitializer {
    private static final String SCRIPTS_PATH = "C:/Users/andre/proiect_java/Catalog_java/scripts";
    
    // Lista scripturilor în ordinea de execuție
    private static final List<String> INIT_SCRIPTS = Arrays.asList(
        "script_tabele.sql",     // First create tables
        "script_secvente.sql",   // Then create sequences
        "script_constraints.sql", // Then add foreign key constraints
        "script_triggere.sql",   // Then create triggers
        "admin_procedures.sql",  // Then create procedures
        "catalog.sql"           // Finally insert initial data
    );

    public static void initializeDatabase() {
        try {
            System.out.println("Starting database initialization using SQL*Plus...");
            
            // First run cleanup
            executeSqlPlusScript("cleanup.sql");
            System.out.println("Cleanup completed");
            
            // Then run all initialization scripts in order
            for (String script : INIT_SCRIPTS) {
                System.out.println("\nExecuting " + script + "...");
                executeSqlPlusScript(script);
                System.out.println(script + " completed");
                Thread.sleep(1000); // Wait a bit between scripts
            }
            
            System.out.println("\nDatabase initialization completed successfully!");

            // Verify database connection after initialization
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Try a simple query to verify the connection
                try (Statement stmt = conn.createStatement()) {
                    try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM studenti")) {
                        if (rs.next()) {
                            System.out.println("Database connection and tables verified successfully!");
                        }
                    }
                }
            } catch (SQLException e) {
                throw new Exception("Failed to verify database connection after initialization: " + e.getMessage());
            }
        } catch (Exception e) {
            String errorMessage = "Error initializing database: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void executeSqlPlusScript(String scriptName) throws Exception {
        String scriptPath = SCRIPTS_PATH + "/" + scriptName;
        
        // Build the SQL*Plus command with correct credentials and exit command
        String command = String.format(
            "sqlplus -S -L catalog/user@XE @\"%s\"",
            scriptPath
        );
        
        // Create process builder
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        // Set the command
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("bash", "-c", command);
        }
        
        // Redirect error stream to output stream
        processBuilder.redirectErrorStream(true);
        
        System.out.println("Executing command: " + command);
        
        // Start the process
        Process process = processBuilder.start();
        
        // Read the output in a separate thread to prevent blocking
        StringBuilder output = new StringBuilder();
        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    output.append(line).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Error reading process output: " + e.getMessage());
            }
        });
        outputThread.start();
        
        // Wait for the process with timeout
        boolean completed = process.waitFor(15, TimeUnit.SECONDS);
        
        if (!completed) {
            System.out.println("Process taking too long, attempting to terminate...");
            // Try to send exit command to sqlplus
            try {
                process.getOutputStream().write("exit\n".getBytes());
                process.getOutputStream().flush();
            } catch (IOException e) {
                System.err.println("Failed to send exit command: " + e.getMessage());
            }
            
            // Wait a bit for graceful shutdown
            Thread.sleep(2000);
            
            if (process.isAlive()) {
                process.destroy();
                Thread.sleep(1000);
                if (process.isAlive()) {
                    process.destroyForcibly();
                    System.out.println("Process forcibly terminated");
                }
            }
            
            // Don't throw exception here, check the output first
        }
        
        // Wait for output thread to finish
        outputThread.join(2000);
        
        // Get exit code if possible
        int exitCode;
        try {
            exitCode = process.exitValue();
            System.out.println("Process completed with exit code: " + exitCode);
        } catch (IllegalThreadStateException e) {
            System.err.println("Could not get exit code, process might still be running");
            exitCode = -1;
        }
        
        // Check for errors in the output
        String outputStr = output.toString();
        if (outputStr.contains("ORA-") || outputStr.contains("SP2-") || 
            (exitCode != 0 && exitCode != 130 && exitCode != -1)) {
            System.err.println("Full output from failed script:");
            System.err.println(outputStr);
            throw new Exception("Script execution failed with errors. Check the output above.");
        }
        
        // If we got here without errors, consider it a success
        System.out.println(scriptName + " completed successfully");
    }

    public static void cleanupDatabase() {
        try {
            System.out.println("Starting database cleanup using SQL*Plus...");
            executeSqlPlusScript("cleanup.sql");
            System.out.println("Database cleanup completed successfully!");
        } catch (Exception e) {
            String errorMessage = "Error during database cleanup: " + e.getMessage();
            System.err.println(errorMessage);
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 
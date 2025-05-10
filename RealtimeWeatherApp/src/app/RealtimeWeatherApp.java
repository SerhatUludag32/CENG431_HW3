package app;

import controller.WeatherController;
import domain.WeatherAppException;
import ui.MainWindow;
import javax.swing.*;
import java.awt.*;

public class RealtimeWeatherApp {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set look and feel: " + e.getMessage());
        }

        // Create and show GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                WeatherController controller = new WeatherController();
                MainWindow mainWindow = new MainWindow(controller);
                mainWindow.setVisible(true);
            } catch (WeatherAppException e) {
                JOptionPane.showMessageDialog(null,
                    "Failed to initialize application: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
} 
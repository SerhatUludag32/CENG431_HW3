package util;

import domain.WeatherAppException;
import javax.swing.JLabel;
import java.awt.Color;

public class ErrorHandler {
    public static void handleError(JLabel statusLabel, Exception e) {
        String message = e instanceof WeatherAppException ? 
            e.getMessage() : 
            "An unexpected error occurred: " + e.getMessage();
        statusLabel.setText("Error: " + message);
        statusLabel.setForeground(Color.RED);
    }

    public static void handleError(JLabel statusLabel, String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setForeground(Color.RED);
    }

    public static void handleSuccess(JLabel statusLabel, String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.GREEN);
    }
} 
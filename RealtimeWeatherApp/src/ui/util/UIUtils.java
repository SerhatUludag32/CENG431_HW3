package ui.util;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    public static JLabel createStatusLabel() {
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        return statusLabel;
    }

    public static void setStatus(JLabel statusLabel, String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    public static void setSuccessStatus(JLabel statusLabel, String message) {
        setStatus(statusLabel, message, Color.GREEN);
    }

    public static void setErrorStatus(JLabel statusLabel, String message) {
        setStatus(statusLabel, message, Color.RED);
    }

    public static void setInfoStatus(JLabel statusLabel, String message) {
        setStatus(statusLabel, message, Color.BLACK);
    }
} 
package util;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    private static final int SUCCESS_MESSAGE_DURATION = 1000; // 1 saniye

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
        
        // Başarı mesajını 1 saniye sonra temizle
        Timer timer = new Timer(SUCCESS_MESSAGE_DURATION, e -> {
            statusLabel.setText(" ");
            ((Timer)e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void setErrorStatus(JLabel statusLabel, String message) {
        setStatus(statusLabel, message, Color.RED);
    }

    public static void setInfoStatus(JLabel statusLabel, String message) {
        setStatus(statusLabel, message, Color.BLACK);
    }
} 
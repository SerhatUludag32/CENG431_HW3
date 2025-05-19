package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import domain.WeatherReading;
import domain.WeatherUnit;
import util.DateUtils;
import util.UIUtils;
import util.UIConstants;
import util.WeatherDataFormatter;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class WeatherInfoPanel extends JPanel {
    private final WeatherController controller;
    private final JTextArea weatherInfo;
    private final JLabel statusLabel;
    private WeatherUnit currentUnit;
    private String selectedCity;
    private LocalDate selectedDate;

    public WeatherInfoPanel(WeatherController controller) {
        this.controller = controller;
        this.currentUnit = controller.getCurrentUnit();
        this.selectedDate = LocalDate.now();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(UIConstants.WEATHER_INFO_TITLE));
        
        // Status label
        statusLabel = UIUtils.createStatusLabel();
        add(statusLabel, BorderLayout.NORTH);
        
        // Date selection panel
        JPanel datePanel = new JPanel(new FlowLayout());
        JLabel dateLabel = new JLabel("Select Date:");
        JTextField dateField = new JTextField(10);
        dateField.setText(DateUtils.formatDate(selectedDate));
        JButton dateButton = new JButton(UIConstants.UPDATE_BUTTON);

        dateButton.addActionListener(e -> {
            try {
                String dateStr = dateField.getText();
                if (DateUtils.isValidDateFormat(dateStr)) {
                    selectedDate = DateUtils.parseDate(dateStr);
                    updateWeatherData(controller.getDataset().getReadings());
                } else {
                    UIUtils.setErrorStatus(statusLabel, DateUtils.getDateFormatHint());
                }
            } catch (Exception ex) {
                UIUtils.setErrorStatus(statusLabel, ex.getMessage());
            }
        });

        datePanel.add(dateLabel);
        datePanel.add(dateField);
        datePanel.add(dateButton);
        add(datePanel, BorderLayout.SOUTH);
        
        weatherInfo = new JTextArea();
        weatherInfo.setEditable(false);
        weatherInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(weatherInfo);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateWeatherData(List<WeatherReading> readings) {
        try {
            if (selectedCity == null) {
                weatherInfo.setText(WeatherDataFormatter.formatSelectCityMessage());
                return;
            }

            List<WeatherReading> cityReadings = controller.getWeatherForCity(selectedCity);
            if (cityReadings.isEmpty()) {
                weatherInfo.setText(WeatherDataFormatter.formatNoDataMessage(selectedCity));
                return;
            }

            // Filter readings for selected date
            WeatherReading reading = cityReadings.stream()
                .filter(r -> r.getDate().equals(selectedDate))
                .findFirst()
                .orElse(null);

            if (reading == null) {
                weatherInfo.setText(WeatherDataFormatter.formatNoDataMessage(
                    selectedCity, DateUtils.formatDate(selectedDate)));
                return;
            }

            weatherInfo.setText(WeatherDataFormatter.formatWeatherInfo(reading, currentUnit));
            UIUtils.setSuccessStatus(statusLabel, UIConstants.DATA_UPDATED_MESSAGE);
        } catch (WeatherAppException e) {
            weatherInfo.setText("");
            UIUtils.setErrorStatus(statusLabel, e.getMessage());
        }
    }

    public void updateUnit(WeatherUnit unit) {
        this.currentUnit = unit;
        updateWeatherData(controller.getDataset().getReadings());
    }

    public void setSelectedCity(String city) {
        this.selectedCity = city;
        updateWeatherData(controller.getDataset().getReadings());
    }
} 
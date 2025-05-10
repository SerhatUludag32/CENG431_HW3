package ui;

import controller.WeatherController;
import domain.WeatherReading;
import domain.WeatherUnit;
import domain.WeatherAppException;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherInfoPanel extends JPanel {
    private final WeatherController controller;
    private final JTextArea weatherInfo;
    private final JLabel statusLabel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private WeatherUnit currentUnit;
    private String selectedCity;
    private LocalDate selectedDate;

    public WeatherInfoPanel(WeatherController controller) {
        this.controller = controller;
        this.currentUnit = controller.getCurrentUnit();
        this.selectedDate = LocalDate.now();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Weather Information"));
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, BorderLayout.NORTH);
        
        // Date selection panel
        JPanel datePanel = new JPanel(new FlowLayout());
        JLabel dateLabel = new JLabel("Select Date:");
        JTextField dateField = new JTextField(10);
        dateField.setText(selectedDate.format(dateFormatter));
        JButton dateButton = new JButton("Update");
        
        dateButton.addActionListener(e -> {
            try {
                String dateStr = dateField.getText();
                selectedDate = LocalDate.parse(dateStr, dateFormatter);
                updateWeatherData(controller.getDataset().getReadings());
            } catch (Exception ex) {
                statusLabel.setText("Invalid date format. Use YYYY-MM-DD");
                statusLabel.setForeground(Color.RED);
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
                weatherInfo.setText("Please select a city to view weather information");
                return;
            }

            List<WeatherReading> cityReadings = controller.getWeatherForCity(selectedCity);
            if (cityReadings.isEmpty()) {
                weatherInfo.setText("No weather data available for " + selectedCity);
                return;
            }

            // Filter readings for selected date
            WeatherReading reading = cityReadings.stream()
                .filter(r -> r.getDate().equals(selectedDate))
                .findFirst()
                .orElse(null);

            if (reading == null) {
                weatherInfo.setText("No weather data available for " + selectedCity + 
                    " on " + selectedDate.format(dateFormatter));
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("City: %s\n", selectedCity));
            sb.append(String.format("Date: %s\n", reading.getDate().format(dateFormatter)));
            sb.append(String.format("Temperature: %.1f%s\n", 
                reading.getTemperature(currentUnit), currentUnit.getSymbol()));
            sb.append(String.format("Humidity: %.1f%%\n", reading.getHumidity()));
            sb.append(String.format("Wind Speed: %.1f km/h\n", reading.getWindSpeed()));
            
            weatherInfo.setText(sb.toString());
            statusLabel.setText("Weather data updated successfully");
            statusLabel.setForeground(Color.GREEN);
        } catch (WeatherAppException e) {
            weatherInfo.setText("");
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
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
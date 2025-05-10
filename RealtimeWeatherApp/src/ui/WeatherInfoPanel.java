package ui;

import controller.WeatherController;
import domain.WeatherReading;
import domain.WeatherUnit;
import domain.WeatherAppException;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherInfoPanel extends JPanel {
    private final WeatherController controller;
    private final JTextArea weatherInfo;
    private final JLabel statusLabel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private WeatherUnit currentUnit;
    private String selectedCity;

    public WeatherInfoPanel(WeatherController controller) {
        this.controller = controller;
        this.currentUnit = controller.getCurrentUnit();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Weather Information"));
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, BorderLayout.NORTH);
        
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

            StringBuilder sb = new StringBuilder();
            for (WeatherReading reading : cityReadings) {
                sb.append(String.format("Date: %s\n", reading.getDate().format(dateFormatter)));
                sb.append(String.format("Temperature: %.1f%s\n", 
                    reading.getTemperature(currentUnit), currentUnit.getSymbol()));
                sb.append(String.format("Humidity: %.1f%%\n", reading.getHumidity()));
                sb.append(String.format("Wind Speed: %.1f km/h\n", reading.getWindSpeed()));
                sb.append("-------------------\n");
            }
            
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
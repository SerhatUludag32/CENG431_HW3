package ui;

import controller.WeatherController;
import domain.WeatherReading;
import domain.WeatherUnit;
import javax.swing.*;
import java.awt.*;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private final WeatherController controller;
    private final JTextArea statsInfo;
    private WeatherUnit currentUnit;

    public StatsPanel(WeatherController controller) {
        this.controller = controller;
        this.currentUnit = controller.getCurrentUnit();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Weather Statistics"));
        
        statsInfo = new JTextArea();
        statsInfo.setEditable(false);
        statsInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(statsInfo);
        add(scrollPane, BorderLayout.CENTER);
        
        updateStatistics();
    }

    public void updateStatistics() {
        List<WeatherReading> readings = controller.getDataset().getReadings();
        StringBuilder sb = new StringBuilder();
        
        // Highest average temperature
        String highestAvgTempCity = findCityWithHighestAverageTemperature(readings);
        sb.append("City with highest average temperature: ").append(highestAvgTempCity).append("\n");
        
        // Lowest average temperature
        String lowestAvgTempCity = findCityWithLowestAverageTemperature(readings);
        sb.append("City with lowest average temperature: ").append(lowestAvgTempCity).append("\n");
        
        // Lowest temperature in January
        String lowestJanTempCity = findCityWithLowestTemperatureInMonth(readings, Month.JANUARY);
        sb.append("City with lowest temperature in January: ").append(lowestJanTempCity).append("\n");
        
        // Highest average humidity in May
        String highestMayHumidityCity = findCityWithHighestAverageHumidityInMonth(readings, Month.MAY);
        sb.append("City with highest average humidity in May: ").append(highestMayHumidityCity).append("\n");
        
        // Highest average wind speed in April
        String highestAprWindCity = findCityWithHighestAverageWindSpeedInMonth(readings, Month.APRIL);
        sb.append("City with highest average wind speed in April: ").append(highestAprWindCity).append("\n");
        
        statsInfo.setText(sb.toString());
    }

    public void updateUnit(WeatherUnit unit) {
        this.currentUnit = unit;
        updateStatistics();
    }

    private String findCityWithHighestAverageTemperature(List<WeatherReading> readings) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(r -> r.getTemperature(currentUnit))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    private String findCityWithLowestAverageTemperature(List<WeatherReading> readings) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(r -> r.getTemperature(currentUnit))))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    private String findCityWithLowestTemperatureInMonth(List<WeatherReading> readings, Month month) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.minBy((r1, r2) -> 
                        Double.compare(r1.getTemperature(currentUnit), r2.getTemperature(currentUnit)))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue((o1, o2) -> 
                    Double.compare(o1.get().getTemperature(currentUnit), o2.get().getTemperature(currentUnit))))
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    private String findCityWithHighestAverageHumidityInMonth(List<WeatherReading> readings, Month month) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(WeatherReading::getHumidity)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    private String findCityWithHighestAverageWindSpeedInMonth(List<WeatherReading> readings, Month month) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(WeatherReading::getWindSpeed)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
} 
package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import domain.WeatherReading;
import domain.WeatherUnit;
import util.UIUtils;
import util.UIConstants;
import util.WeatherStatsCalculator;
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
        setBorder(BorderFactory.createTitledBorder(UIConstants.WEATHER_STATS_TITLE));
        
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
        String highestAvgTempCity = WeatherStatsCalculator.findCityWithHighestAverageTemperature(readings, currentUnit);
        sb.append("City with highest average temperature: ").append(highestAvgTempCity).append("\n");
        
        // Lowest average temperature
        String lowestAvgTempCity = WeatherStatsCalculator.findCityWithLowestAverageTemperature(readings, currentUnit);
        sb.append("City with lowest average temperature: ").append(lowestAvgTempCity).append("\n");
        
        // Lowest temperature in January
        String lowestJanTempCity = WeatherStatsCalculator.findCityWithLowestTemperatureInMonth(readings, Month.JANUARY, currentUnit);
        sb.append("City with lowest temperature in January: ").append(lowestJanTempCity).append("\n");
        
        // Highest average humidity in May
        String highestMayHumidityCity = WeatherStatsCalculator.findCityWithHighestAverageHumidityInMonth(readings, Month.MAY);
        sb.append("City with highest average humidity in May: ").append(highestMayHumidityCity).append("\n");
        
        // Highest average wind speed in April
        String highestAprWindCity = WeatherStatsCalculator.findCityWithHighestAverageWindSpeedInMonth(readings, Month.APRIL);
        sb.append("City with highest average wind speed in April: ").append(highestAprWindCity).append("\n");
        
        statsInfo.setText(sb.toString());
    }

    public void updateUnit(WeatherUnit unit) {
        this.currentUnit = unit;
        updateStatistics();
    }
} 
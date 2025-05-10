package ui;

import controller.WeatherController;
import domain.WeatherUnit;
import domain.WeatherAppException;
import observer.WeatherDataListener;
import domain.WeatherReading;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame implements WeatherDataListener {
    private final WeatherController controller;
    private final CityListPanel cityListPanel;
    private final WeatherInfoPanel weatherInfoPanel;
    private final UnitTogglePanel unitTogglePanel;
    private final StatsPanel statsPanel;
    private final JLabel statusLabel;

    public MainWindow(WeatherController controller) {
        this.controller = controller;
        this.controller.getDataset().addListener(this);

        setTitle("Realtime Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, BorderLayout.NORTH);

        // Initialize panels
        cityListPanel = new CityListPanel(controller);
        weatherInfoPanel = new WeatherInfoPanel(controller);
        unitTogglePanel = new UnitTogglePanel(controller);
        statsPanel = new StatsPanel(controller);

        // Layout setup
        setLayout(new BorderLayout());
        add(cityListPanel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(weatherInfoPanel, BorderLayout.CENTER);
        centerPanel.add(unitTogglePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        add(statsPanel, BorderLayout.SOUTH);

        // Connect city selection
        cityListPanel.getCityList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCity = cityListPanel.getSelectedCity();
                weatherInfoPanel.setSelectedCity(selectedCity);
            }
        });
    }

    @Override
    public void onWeatherDataUpdated(List<WeatherReading> readings) {
        try {
            weatherInfoPanel.updateWeatherData(readings);
            statsPanel.updateStatistics();
            statusLabel.setText("Data updated successfully");
            statusLabel.setForeground(Color.GREEN);
        } catch (WeatherAppException e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    @Override
    public void onTrackedCitiesUpdated(List<String> cities) {
        try {
            cityListPanel.updateCityList(cities);
            statusLabel.setText("City list updated successfully");
            statusLabel.setForeground(Color.GREEN);
        } catch (WeatherAppException e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    @Override
    public void onUnitChanged(WeatherUnit unit) {
        try {
            weatherInfoPanel.updateUnit(unit);
            statsPanel.updateUnit(unit);
            statusLabel.setText("Unit changed successfully");
            statusLabel.setForeground(Color.GREEN);
        } catch (WeatherAppException e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }
} 
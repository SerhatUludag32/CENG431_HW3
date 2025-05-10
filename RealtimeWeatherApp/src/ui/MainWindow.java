package ui;

import controller.WeatherController;
import domain.WeatherUnit;
import domain.WeatherAppException;
import observer.WeatherDataListener;
import domain.WeatherReading;
import ui.util.UIUtils;
import ui.util.UIConstants;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame implements WeatherDataListener {
    private final AvailableCitiesPanel availableCitiesPanel;
    private final CityListPanel trackedCitiesPanel;
    private final WeatherInfoPanel weatherInfoPanel;
    private final StatsPanel statsPanel;
    private final JLabel statusLabel;
    private String lastSelectedCity = null;

    public MainWindow(WeatherController controller) {
        controller.getDataset().addListener(this);

        setTitle("Realtime Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Status label
        statusLabel = UIUtils.createStatusLabel();
        add(statusLabel, BorderLayout.NORTH);

        // Initialize panels
        availableCitiesPanel = new AvailableCitiesPanel(controller);
        trackedCitiesPanel = new CityListPanel(controller);
        weatherInfoPanel = new WeatherInfoPanel(controller);
        UnitTogglePanel unitTogglePanel = new UnitTogglePanel(controller);
        statsPanel = new StatsPanel(controller);

        // Layout setup
        setLayout(new BorderLayout());
        
        // Left panel with available and tracked cities
        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            availableCitiesPanel, trackedCitiesPanel);
        leftSplitPane.setDividerLocation(400);
        add(leftSplitPane, BorderLayout.WEST);
        
        // Center panel with weather info and unit toggle
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(weatherInfoPanel, BorderLayout.CENTER);
        centerPanel.add(unitTogglePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        add(statsPanel, BorderLayout.SOUTH);

        // Connect city selection from available cities
        availableCitiesPanel.getCityList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCity = availableCitiesPanel.getSelectedCity();
                if (selectedCity != null) {
                    trackedCitiesPanel.getCityList().clearSelection();
                    lastSelectedCity = selectedCity;
                    weatherInfoPanel.setSelectedCity(selectedCity);
                }
            }
        });

        // Connect city selection from tracked cities
        trackedCitiesPanel.getCityList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCity = trackedCitiesPanel.getSelectedCity();
                if (selectedCity != null) {
                    availableCitiesPanel.getCityList().clearSelection();
                    lastSelectedCity = selectedCity;
                    weatherInfoPanel.setSelectedCity(selectedCity);
                }
            }
        });

        // --- Initial selection logic ---
        List<String> trackedCities = controller.getTrackedCities();
        if (!trackedCities.isEmpty()) {
            trackedCitiesPanel.getCityList().setSelectedIndex(0);
            availableCitiesPanel.getCityList().clearSelection();
            lastSelectedCity = trackedCities.getFirst();
            weatherInfoPanel.setSelectedCity(trackedCities.getFirst());
        } else {
            trackedCitiesPanel.getCityList().clearSelection();
            availableCitiesPanel.getCityList().clearSelection();
            lastSelectedCity = null;
        }
    }

    @Override
    public void onWeatherDataUpdated(List<WeatherReading> readings) {
        try {
            weatherInfoPanel.updateWeatherData(readings);
            statsPanel.updateStatistics();
            UIUtils.setSuccessStatus(statusLabel, UIConstants.DATA_UPDATED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    @Override
    public void onTrackedCitiesUpdated(List<String> cities) {
        try {
            trackedCitiesPanel.updateCityList(cities);
            UIUtils.setSuccessStatus(statusLabel, UIConstants.CITY_LIST_UPDATED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    @Override
    public void onUnitChanged(WeatherUnit unit) {
        try {
            weatherInfoPanel.updateUnit(unit);
            statsPanel.updateUnit(unit);
            UIUtils.setSuccessStatus(statusLabel, UIConstants.UNIT_CHANGED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }
} 
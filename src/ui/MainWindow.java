package ui;

import controller.WeatherController;
import domain.WeatherUnit;
import domain.WeatherAppException;
import observer.WeatherDataListener;
import domain.WeatherReading;
import util.UIUtils;
import util.UIConstants;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.File;

public class MainWindow extends JFrame implements WeatherDataListener {
    private final WeatherController controller;
    private final JLabel statusLabel;
    private final WeatherPanelManager panelManager;

    public MainWindow(WeatherController controller) {
        this.controller = controller;
        this.statusLabel = UIUtils.createStatusLabel();
        this.panelManager = new WeatherPanelManager(controller);
        controller.getDataset().addListener(this);

        setupWindow();
        setupStatusLabel();
        setupLayout();
        initializePanels();
        setupInitialSelection();
    }

    private void setupWindow() {
        setTitle("Realtime Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setupStatusLabel() {
        add(statusLabel, BorderLayout.NORTH);
    }

    private void setupLayout() {
        // Left panel with available and tracked cities
        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            panelManager.getAvailableCitiesPanel(),
            panelManager.getTrackedCitiesPanel());
        leftSplitPane.setDividerLocation(400);
        add(leftSplitPane, BorderLayout.WEST);
        
        // Center panel with weather info and unit toggle
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(panelManager.getWeatherInfoPanel(), BorderLayout.CENTER);
        centerPanel.add(panelManager.getUnitTogglePanel(), BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        add(panelManager.getStatsPanel(), BorderLayout.SOUTH);
    }

    private void initializePanels() {
        panelManager.initializePanels();
        panelManager.setupCitySelectionListeners(this::handleCitySelection);
    }

    private void setupInitialSelection() {
        List<String> trackedCities = controller.getTrackedCities();
        if (!trackedCities.isEmpty()) {
            panelManager.selectFirstTrackedCity(trackedCities.get(0));
        }
    }

    private void handleCitySelection(String selectedCity, boolean fromTracked) {
        if (selectedCity != null) {
            panelManager.clearOtherSelection(fromTracked);
            panelManager.updateWeatherInfo(selectedCity);
        }
    }

    @Override
    public void onWeatherDataUpdated(List<WeatherReading> readings) {
        try {
            panelManager.updateWeatherData(readings);
            UIUtils.setSuccessStatus(statusLabel, UIConstants.DATA_UPDATED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    @Override
    public void onTrackedCitiesUpdated(List<String> cities) {
        try {
            panelManager.updateTrackedCities(cities);
            UIUtils.setSuccessStatus(statusLabel, UIConstants.CITY_LIST_UPDATED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    @Override
    public void onUnitChanged(WeatherUnit unit) {
        try {
            panelManager.updateUnit(unit);
            UIUtils.setSuccessStatus(statusLabel, UIConstants.UNIT_CHANGED_MESSAGE);
        } catch (WeatherAppException e) {
            UIUtils.setErrorStatus(statusLabel, UIConstants.ERROR_PREFIX + e.getMessage());
        }
    }
} 
package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import domain.WeatherReading;
import domain.WeatherUnit;
import javax.swing.*;
import java.util.List;
import java.util.function.BiConsumer;

public class WeatherPanelManager {
    private final WeatherController controller;
    private final AvailableCitiesPanel availableCitiesPanel;
    private final CityListPanel trackedCitiesPanel;
    private final WeatherInfoPanel weatherInfoPanel;
    private final StatsPanel statsPanel;
    private final UnitTogglePanel unitTogglePanel;

    public WeatherPanelManager(WeatherController controller) {
        this.controller = controller;
        this.availableCitiesPanel = new AvailableCitiesPanel(controller);
        this.trackedCitiesPanel = new CityListPanel(controller);
        this.weatherInfoPanel = new WeatherInfoPanel(controller);
        this.statsPanel = new StatsPanel(controller);
        this.unitTogglePanel = new UnitTogglePanel(controller);
    }

    public void initializePanels() {
        availableCitiesPanel.updateCityList(controller.getAvailableCities());
        trackedCitiesPanel.updateCityList(controller.getTrackedCities());
    }

    public void setupCitySelectionListeners(BiConsumer<String, Boolean> selectionHandler) {
        availableCitiesPanel.getCityList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionHandler.accept(availableCitiesPanel.getSelectedCity(), false);
            }
        });

        trackedCitiesPanel.getCityList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionHandler.accept(trackedCitiesPanel.getSelectedCity(), true);
            }
        });
    }

    public void selectFirstTrackedCity(String city) {
        trackedCitiesPanel.getCityList().setSelectedIndex(0);
        availableCitiesPanel.getCityList().clearSelection();
        weatherInfoPanel.setSelectedCity(city);
    }

    public void clearOtherSelection(boolean fromTracked) {
        if (fromTracked) {
            availableCitiesPanel.getCityList().clearSelection();
        } else {
            trackedCitiesPanel.getCityList().clearSelection();
        }
    }

    public void updateWeatherInfo(String city) {
        weatherInfoPanel.setSelectedCity(city);
    }

    public void updateWeatherData(List<WeatherReading> readings) throws WeatherAppException {
        weatherInfoPanel.updateWeatherData(readings);
        statsPanel.updateStatistics();
    }

    public void updateTrackedCities(List<String> cities) throws WeatherAppException {
        trackedCitiesPanel.updateCityList(cities);
    }

    public void updateUnit(WeatherUnit unit) throws WeatherAppException {
        weatherInfoPanel.updateUnit(unit);
        statsPanel.updateUnit(unit);
    }

    // Getter methods
    public JPanel getAvailableCitiesPanel() { return availableCitiesPanel; }
    public JPanel getTrackedCitiesPanel() { return trackedCitiesPanel; }
    public JPanel getWeatherInfoPanel() { return weatherInfoPanel; }
    public JPanel getStatsPanel() { return statsPanel; }
    public JPanel getUnitTogglePanel() { return unitTogglePanel; }
} 
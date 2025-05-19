package ui;

import controller.WeatherController;
import javax.swing.*;

public class WeatherPanelFactory {
    public static JPanel createAvailableCitiesPanel(WeatherController controller) {
        return new AvailableCitiesPanel(controller);
    }

    public static JPanel createTrackedCitiesPanel(WeatherController controller) {
        return new CityListPanel(controller);
    }

    public static JPanel createWeatherInfoPanel(WeatherController controller) {
        return new WeatherInfoPanel(controller);
    }

    public static JPanel createStatsPanel(WeatherController controller) {
        return new StatsPanel(controller);
    }

    public static JPanel createUnitTogglePanel(WeatherController controller) {
        return new UnitTogglePanel(controller);
    }
} 
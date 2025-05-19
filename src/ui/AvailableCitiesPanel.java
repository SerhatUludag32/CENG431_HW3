package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import util.UIConstants;
import javax.swing.*;
import java.awt.*;

public class AvailableCitiesPanel extends BaseCityPanel {

    public AvailableCitiesPanel(WeatherController controller) {
        super(controller, UIConstants.AVAILABLE_CITIES_TITLE);
    }

    @Override
    protected void initializePanel() {
        // Track button
        JButton trackButton = new JButton(UIConstants.TRACK_CITY_BUTTON);
        trackButton.addActionListener(e -> {
            try {
                String selectedCity = getSelectedCity();
                if (selectedCity != null) {
                    controller.addTrackedCity(selectedCity);
                    setStatus(UIConstants.CITY_ADDED_MESSAGE, UIConstants.SUCCESS_COLOR);
                } else {
                    setStatus(UIConstants.SELECT_CITY_MESSAGE, UIConstants.ERROR_COLOR);
                }
            } catch (WeatherAppException ex) {
                setStatus(UIConstants.ERROR_PREFIX + ex.getMessage(), UIConstants.ERROR_COLOR);
            }
        });
        
        add(trackButton, BorderLayout.SOUTH);
        
        // Initial update with all available cities
        updateCityList(controller.getAvailableCities());
    }
} 
package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import java.awt.*;
import javax.swing.*;
import util.UIConstants;

public class CityListPanel extends BaseCityPanel {

    public CityListPanel(WeatherController controller) {
        super(controller, UIConstants.TRACKED_CITIES_TITLE);
    }

    @Override
    protected void initializePanel() {
        // Remove button
        JButton removeButton = new JButton(UIConstants.REMOVE_CITY_BUTTON);
        removeButton.addActionListener(e -> {
            try {
                String selectedCity = getSelectedCity();
                if (selectedCity != null) {
                    controller.removeTrackedCity(selectedCity);
                    setStatus(UIConstants.CITY_REMOVED_MESSAGE, UIConstants.SUCCESS_COLOR);
                } else {
                    setStatus(UIConstants.SELECT_CITY_TO_REMOVE_MESSAGE, UIConstants.ERROR_COLOR);
                }
            } catch (WeatherAppException ex) {
                setStatus(UIConstants.ERROR_PREFIX + ex.getMessage(), UIConstants.ERROR_COLOR);
            }
        });

        add(removeButton, BorderLayout.SOUTH);

        // Initial update with tracked cities
        updateCityList(controller.getTrackedCities());
    }
}

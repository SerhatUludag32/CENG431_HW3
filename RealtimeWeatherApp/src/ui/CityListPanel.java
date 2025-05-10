package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CityListPanel extends JPanel {
    private final WeatherController controller;
    private final JList<String> cityList;
    private final DefaultListModel<String> listModel;
    private final JButton removeButton;
    private final JLabel statusLabel;

    public CityListPanel(WeatherController controller) {
        this.controller = controller;
        this.listModel = new DefaultListModel<>();
        this.cityList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tracked Cities"));
        
        // City list
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                notifyCitySelectionChanged();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(cityList);
        add(scrollPane, BorderLayout.CENTER);
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, BorderLayout.NORTH);
        
        // Remove button
        removeButton = new JButton("Remove from Tracked");
        removeButton.addActionListener(e -> {
            try {
                String selectedCity = cityList.getSelectedValue();
                if (selectedCity != null) {
                    controller.removeTrackedCity(selectedCity);
                    statusLabel.setText("City removed from tracked cities");
                    statusLabel.setForeground(Color.GREEN);
                } else {
                    statusLabel.setText("Please select a city to remove");
                    statusLabel.setForeground(Color.RED);
                }
            } catch (WeatherAppException ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        });
        
        add(removeButton, BorderLayout.SOUTH);
        
        // Initial update with tracked cities
        updateCityList(controller.getTrackedCities());
    }

    public void updateCityList(List<String> cities) {
        String selectedCity = cityList.getSelectedValue();
        listModel.clear();
        
        // Add cities in reverse order to maintain the order of addition
        // (most recently added at the top)
        for (int i = cities.size() - 1; i >= 0; i--) {
            listModel.add(0, cities.get(i));
        }
        
        // Restore selection if possible
        if (selectedCity != null && cities.contains(selectedCity)) {
            cityList.setSelectedValue(selectedCity, true);
        } // else do not auto-select any city
    }

    public String getSelectedCity() {
        return cityList.getSelectedValue();
    }

    public JList<String> getCityList() {
        return cityList;
    }

    private void notifyCitySelectionChanged() {
        String selectedCity = getSelectedCity();
        if (selectedCity != null) {
            statusLabel.setText("Selected city: " + selectedCity);
            statusLabel.setForeground(Color.BLACK);
        }
    }
} 
package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AvailableCitiesPanel extends JPanel {
    private final WeatherController controller;
    private final JList<String> cityList;
    private final DefaultListModel<String> listModel;
    private final JButton trackButton;
    private final JLabel statusLabel;

    public AvailableCitiesPanel(WeatherController controller) {
        this.controller = controller;
        this.listModel = new DefaultListModel<>();
        this.cityList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Available Cities"));
        
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
        
        // Track button
        trackButton = new JButton("Track City");
        trackButton.addActionListener(e -> {
            try {
                String selectedCity = cityList.getSelectedValue();
                if (selectedCity != null) {
                    controller.addTrackedCity(selectedCity);
                    statusLabel.setText("City added to tracked cities");
                    statusLabel.setForeground(Color.GREEN);
                } else {
                    statusLabel.setText("Please select a city first");
                    statusLabel.setForeground(Color.RED);
                }
            } catch (WeatherAppException ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        });
        
        add(trackButton, BorderLayout.SOUTH);
        
        // Initial update with all available cities
        updateCityList(controller.getAvailableCities());
    }

    public void updateCityList(List<String> cities) {
        String selectedCity = cityList.getSelectedValue();
        listModel.clear();
        for (String city : cities) {
            listModel.addElement(city);
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
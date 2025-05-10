package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CityListPanel extends JPanel {
    private final WeatherController controller;
    private final JList<String> cityList;
    private final DefaultListModel<String> listModel;
    private final JButton addButton;
    private final JButton removeButton;
    private final JLabel statusLabel;

    public CityListPanel(WeatherController controller) {
        this.controller = controller;
        this.listModel = new DefaultListModel<>();
        this.cityList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Cities"));
        
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
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        
        addButton.addActionListener(e -> {
            try {
                String city = JOptionPane.showInputDialog(this, "Enter city name:");
                if (city != null && !city.trim().isEmpty()) {
                    controller.addTrackedCity(city.trim());
                    statusLabel.setText("City added successfully");
                    statusLabel.setForeground(Color.GREEN);
                }
            } catch (WeatherAppException ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        });
        
        removeButton.addActionListener(e -> {
            try {
                String selectedCity = cityList.getSelectedValue();
                if (selectedCity != null) {
                    controller.removeTrackedCity(selectedCity);
                    statusLabel.setText("City removed successfully");
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
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Initial update
        updateCityList(controller.getTrackedCities());
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
        } else if (!cities.isEmpty()) {
            cityList.setSelectedIndex(0);
        }
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
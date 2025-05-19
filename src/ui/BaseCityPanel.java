package ui;

import controller.WeatherController;
import domain.WeatherAppException;
import util.UIUtils;
import util.UIConstants;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class BaseCityPanel extends JPanel {
    protected final WeatherController controller;
    protected final JList<String> cityList;
    protected final DefaultListModel<String> listModel;
    protected final JLabel statusLabel;

    public BaseCityPanel(WeatherController controller, String title) {
        this.controller = controller;
        this.listModel = new DefaultListModel<>();
        this.cityList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(title));
        
        // City list setup
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                notifyCitySelectionChanged();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(cityList);
        add(scrollPane, BorderLayout.CENTER);
        
        // Status label
        statusLabel = UIUtils.createStatusLabel();
        add(statusLabel, BorderLayout.NORTH);
        
        // Initialize panel
        initializePanel();
    }

    protected abstract void initializePanel();

    public void updateCityList(List<String> cities) {
        String selectedCity = cityList.getSelectedValue();
        listModel.clear();
        for (String city : cities) {
            listModel.addElement(city);
        }
        // Restore selection if possible
        if (selectedCity != null && cities.contains(selectedCity)) {
            cityList.setSelectedValue(selectedCity, true);
        }
    }

    public String getSelectedCity() {
        return cityList.getSelectedValue();
    }

    public JList<String> getCityList() {
        return cityList;
    }

    protected void notifyCitySelectionChanged() {
        String selectedCity = getSelectedCity();
        if (selectedCity != null) {
            UIUtils.setInfoStatus(statusLabel, "Selected city: " + selectedCity);
        }
    }

    protected void setStatus(String message, Color color) {
        UIUtils.setStatus(statusLabel, message, color);
    }
} 
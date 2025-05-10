package ui;

import controller.WeatherController;
import domain.WeatherUnit;
import javax.swing.*;
import java.awt.*;

public class UnitTogglePanel extends JPanel {
    private final WeatherController controller;
    private final ButtonGroup unitGroup;
    private final JRadioButton celsiusButton;
    private final JRadioButton fahrenheitButton;

    public UnitTogglePanel(WeatherController controller) {
        this.controller = controller;
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Temperature Unit"));
        
        unitGroup = new ButtonGroup();
        celsiusButton = new JRadioButton("Celsius (°C)");
        fahrenheitButton = new JRadioButton("Fahrenheit (°F)");
        
        unitGroup.add(celsiusButton);
        unitGroup.add(fahrenheitButton);
        
        celsiusButton.addActionListener(e -> controller.setTemperatureUnit(WeatherUnit.CELSIUS));
        fahrenheitButton.addActionListener(e -> controller.setTemperatureUnit(WeatherUnit.FAHRENHEIT));
        
        add(celsiusButton);
        add(fahrenheitButton);
        
        // Set initial selection
        celsiusButton.setSelected(true);
    }
} 
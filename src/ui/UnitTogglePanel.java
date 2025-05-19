package ui;

import controller.WeatherController;
import domain.WeatherUnit;
import util.UIConstants;
import javax.swing.*;
import java.awt.*;

public class UnitTogglePanel extends JPanel {

    public UnitTogglePanel(WeatherController controller) {

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder(UIConstants.TEMPERATURE_UNIT_TITLE));

        ButtonGroup unitGroup = new ButtonGroup();
        JRadioButton celsiusButton = new JRadioButton("Celsius (\u00B0C)");
        JRadioButton fahrenheitButton = new JRadioButton("Fahrenheit (\u00B0F)");
        
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
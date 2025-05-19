package domain;

public enum WeatherUnit {
    CELSIUS("\u00B0C"),
    FAHRENHEIT("\u00B0F");

    private final String symbol;

    WeatherUnit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public double convertTemperature(double temperature, WeatherUnit fromUnit) {
        if (fromUnit == this) {
            return temperature;
        }
        if (this == CELSIUS) {
            return (temperature - 32) * 5/9;
        } else {
            return (temperature * 9/5) + 32;
        }
    }
} 
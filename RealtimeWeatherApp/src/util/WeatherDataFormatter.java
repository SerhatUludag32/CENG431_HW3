package util;

import domain.WeatherReading;
import domain.WeatherUnit;
import java.time.format.DateTimeFormatter;

public class WeatherDataFormatter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatWeatherInfo(WeatherReading reading, WeatherUnit unit) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("City: %s\n", reading.getCity()));
        sb.append(String.format("Date: %s\n", reading.getDate().format(DATE_FORMATTER)));
        sb.append(String.format("Temperature: %.1f%s\n", 
            reading.getTemperature(unit), unit.getSymbol()));
        sb.append(String.format("Humidity: %.1f%%\n", reading.getHumidity()));
        sb.append(String.format("Wind Speed: %.1f km/h\n", reading.getWindSpeed()));
        sb.append(String.format("Condition: %s\n", reading.getCondition()));
        return sb.toString();
    }

    public static String formatNoDataMessage(String city, String date) {
        return String.format("No weather data available for %s on %s", city, date);
    }

    public static String formatNoDataMessage(String city) {
        return String.format("No weather data available for %s", city);
    }

    public static String formatSelectCityMessage() {
        return "Please select a city to view weather information";
    }
} 
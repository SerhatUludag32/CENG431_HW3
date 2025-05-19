package domain;

import java.util.List;

public interface WeatherDataFormatter {
    String formatWeatherInfo(WeatherReading reading, WeatherUnit unit);
    String formatNoDataMessage(String city);
    String formatNoDataMessage(String city, String date);
    String formatSelectCityMessage();
    String formatStatistics(List<WeatherReading> readings, WeatherUnit unit);
}

class DefaultWeatherDataFormatter implements WeatherDataFormatter {
    @Override
    public String formatWeatherInfo(WeatherReading reading, WeatherUnit unit) {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(reading.getDate()).append("\n");
        sb.append("Temperature: ").append(reading.getTemperature(unit)).append(unit.getSymbol()).append("\n");
        sb.append("Humidity: ").append(reading.getHumidity()).append("%\n");
        sb.append("Wind Speed: ").append(reading.getWindSpeed()).append(" km/h\n");
        sb.append("Condition: ").append(reading.getCondition());
        return sb.toString();
    }

    @Override
    public String formatNoDataMessage(String city) {
        return "No weather data available for " + city;
    }

    @Override
    public String formatNoDataMessage(String city, String date) {
        return "No weather data available for " + city + " on " + date;
    }

    @Override
    public String formatSelectCityMessage() {
        return "Please select a city to view weather information";
    }

    @Override
    public String formatStatistics(List<WeatherReading> readings, WeatherUnit unit) {
        // İstatistik formatlaması burada yapılacak
        return "Statistics will be implemented here";
    }
} 
package domain;

import java.time.LocalDate;

public class WeatherReading {
    private final LocalDate date;
    private final double temperature;
    private final double humidity;
    private final double windSpeed;
    private final String city;

    public WeatherReading(LocalDate date, double temperature, double humidity, double windSpeed, String city) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTemperature(WeatherUnit unit) {
        return unit.convertTemperature(temperature, WeatherUnit.CELSIUS);
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCity() {
        return city;
    }
} 
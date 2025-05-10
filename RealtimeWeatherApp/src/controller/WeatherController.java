package controller;

import domain.WeatherDataset;
import domain.WeatherReading;
import domain.WeatherUnit;
import domain.WeatherAppException;
import persistence.CsvWeatherDataSource;
import persistence.TrackedCityStore;
import java.io.IOException;
import java.util.List;

public class WeatherController {
    private final WeatherDataset dataset;
    private final CsvWeatherDataSource dataSource;
    private final TrackedCityStore cityStore;

    public WeatherController() {
        this.dataset = new WeatherDataset();
        this.dataSource = new CsvWeatherDataSource();
        this.cityStore = new TrackedCityStore();
        loadInitialData();
    }

    private void loadInitialData() {
        try {
            // Load weather data
            List<WeatherReading> readings = dataSource.loadWeatherData("weather_data.csv");
            dataset.addReadings(readings);

            // Load tracked cities
            List<String> trackedCities = cityStore.loadTrackedCities();
            for (String city : trackedCities) {
                dataset.addTrackedCity(city);
            }
        } catch (IOException e) {
            throw new WeatherAppException("Failed to load initial data", e);
        }
    }

    public void addTrackedCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherAppException("City name cannot be empty");
        }
        
        dataset.addTrackedCity(city);
        try {
            cityStore.saveTrackedCities(dataset.getTrackedCities());
        } catch (IOException e) {
            throw new WeatherAppException("Failed to save tracked cities", e);
        }
    }

    public void removeTrackedCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherAppException("City name cannot be empty");
        }
        
        dataset.removeTrackedCity(city);
        try {
            cityStore.saveTrackedCities(dataset.getTrackedCities());
        } catch (IOException e) {
            throw new WeatherAppException("Failed to save tracked cities", e);
        }
    }

    public void setTemperatureUnit(WeatherUnit unit) {
        if (unit == null) {
            throw new WeatherAppException("Temperature unit cannot be null");
        }
        dataset.setUnit(unit);
    }

    public WeatherDataset getDataset() {
        return dataset;
    }

    public List<WeatherReading> getWeatherForCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherAppException("City name cannot be empty");
        }
        return dataset.getReadings().stream()
                .filter(reading -> reading.getCity().equals(city))
                .toList();
    }

    public List<String> getTrackedCities() {
        return dataset.getTrackedCities();
    }

    public WeatherUnit getCurrentUnit() {
        return dataset.getCurrentUnit();
    }

    public List<String> getAvailableCities() {
        return dataset.getReadings().stream()
            .map(WeatherReading::getCity)
            .distinct()
            .toList();
    }
} 
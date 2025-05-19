package domain;

import observer.WeatherDataListener;
import observer.WeatherDataNotifier;
import java.util.*;

public class WeatherDataset implements WeatherDataNotifier {
    private final List<WeatherReading> readings;
    private final Set<String> trackedCities;
    private final List<WeatherDataListener> listeners;
    private WeatherUnit currentUnit;

    public WeatherDataset() {
        this.readings = new ArrayList<>();
        this.trackedCities = new LinkedHashSet<>();
        this.listeners = new ArrayList<>();
        this.currentUnit = WeatherUnit.CELSIUS;
    }

    public void addReading(WeatherReading reading) {
        readings.add(reading);
        notifyListeners();
    }

    public void addReadings(List<WeatherReading> newReadings) {
        readings.addAll(newReadings);
        notifyListeners();
    }

    public void addTrackedCity(String city) {
        trackedCities.add(city);
        notifyListeners();
    }

    public void removeTrackedCity(String city) {
        trackedCities.remove(city);
        notifyListeners();
    }

    public void setUnit(WeatherUnit unit) {
        this.currentUnit = unit;
        notifyListeners();
    }

    public List<WeatherReading> getReadings() {
        return Collections.unmodifiableList(readings);
    }

    public List<String> getTrackedCities() {
        List<String> cities = new ArrayList<>(trackedCities);
        Collections.reverse(cities);
        return cities;
    }

    public WeatherUnit getCurrentUnit() {
        return currentUnit;
    }

    @Override
    public void addListener(WeatherDataListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(WeatherDataListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (WeatherDataListener listener : listeners) {
            listener.onWeatherDataUpdated(getReadings());
            listener.onTrackedCitiesUpdated(getTrackedCities());
            listener.onUnitChanged(currentUnit);
        }
    }
} 
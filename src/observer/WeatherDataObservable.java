package observer;

import domain.WeatherReading;
import domain.WeatherUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class WeatherDataObservable {
    private final List<WeatherDataListener> listeners = new ArrayList<>();

    public void addListener(WeatherDataListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(WeatherDataListener listener) {
        listeners.remove(listener);
    }

    protected void notifyWeatherDataUpdated(List<WeatherReading> readings) {
        for (WeatherDataListener listener : listeners) {
            listener.onWeatherDataUpdated(readings);
        }
    }

    protected void notifyTrackedCitiesUpdated(List<String> cities) {
        for (WeatherDataListener listener : listeners) {
            listener.onTrackedCitiesUpdated(cities);
        }
    }

    protected void notifyUnitChanged(WeatherUnit unit) {
        for (WeatherDataListener listener : listeners) {
            listener.onUnitChanged(unit);
        }
    }
} 
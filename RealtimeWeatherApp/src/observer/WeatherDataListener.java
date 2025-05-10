package observer;

import domain.WeatherReading;
import java.util.List;

public interface WeatherDataListener {
    void onWeatherDataUpdated(List<WeatherReading> readings);
    void onTrackedCitiesUpdated(List<String> cities);
    void onUnitChanged(domain.WeatherUnit unit);
} 
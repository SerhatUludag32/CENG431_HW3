package observer;

public interface WeatherDataNotifier {
    void addListener(WeatherDataListener listener);
    void removeListener(WeatherDataListener listener);
    void notifyListeners();
} 
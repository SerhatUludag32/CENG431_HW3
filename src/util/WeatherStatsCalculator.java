package util;

import domain.WeatherReading;
import domain.WeatherUnit;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherStatsCalculator {
    public static String findCityWithHighestAverageTemperature(List<WeatherReading> readings, WeatherUnit unit) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(r -> r.getTemperature(unit))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public static String findCityWithLowestAverageTemperature(List<WeatherReading> readings, WeatherUnit unit) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(r -> r.getTemperature(unit))))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public static String findCityWithLowestTemperatureInMonth(List<WeatherReading> readings, Month month, WeatherUnit unit) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.minBy((r1, r2) -> 
                        Double.compare(r1.getTemperature(unit), r2.getTemperature(unit)))))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue((o1, o2) -> 
                    Double.compare(o1.get().getTemperature(unit), o2.get().getTemperature(unit))))
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public static String findCityWithHighestAverageHumidityInMonth(List<WeatherReading> readings, Month month) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(WeatherReading::getHumidity)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public static String findCityWithHighestAverageWindSpeedInMonth(List<WeatherReading> readings, Month month) {
        return readings.stream()
                .filter(r -> r.getDate().getMonth() == month)
                .collect(Collectors.groupingBy(
                    WeatherReading::getCity,
                    Collectors.averagingDouble(WeatherReading::getWindSpeed)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
} 
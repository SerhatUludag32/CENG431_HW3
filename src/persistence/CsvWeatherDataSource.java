package persistence;

import domain.WeatherReading;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvWeatherDataSource {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<WeatherReading> loadWeatherData(String filePath) throws IOException {
        List<WeatherReading> readings = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            // Skip header
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String city = parts[0];
                    LocalDate date = LocalDate.parse(parts[1], DATE_FORMATTER);
                    double temperature = Double.parseDouble(parts[2]);
                    double humidity = Double.parseDouble(parts[3]);
                    double windSpeed = Double.parseDouble(parts[4]);
                    String condition = parts[5];
                    
                    readings.add(new WeatherReading(date, temperature, humidity, windSpeed, city, condition));
                }
            }
        }
        
        return readings;
    }
} 
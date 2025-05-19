package persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrackedCityStore {
    private static final String STORE_FILE = "tracked_cities.txt";

    public void saveTrackedCities(List<String> cities) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(STORE_FILE), StandardCharsets.UTF_8))) {
            for (String city : cities) {
                writer.println(city);
            }
        }
    }

    public List<String> loadTrackedCities() throws IOException {
        List<String> cities = new ArrayList<>();
        File file = new File(STORE_FILE);
        
        if (!file.exists()) {
            return cities;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    cities.add(line.trim());
                }
            }
        }
        
        return cities;
    }
} 

# Realtime Weather Application – Refactored Version

This Java Swing application displays real-time and historical weather information for selected cities. It adheres to SOLID principles, GRASP responsibilities, and follows the MVC and Observer design patterns.

## 🗂 Project Structure

```
src/
├── app/                        # Main entry point
├── controller/                 # Controls data flow between model and view
├── domain/                     # Core domain models (WeatherReading, Dataset, Unit)
├── observer/                   # Observer pattern interfaces and logic
├── persistence/                # CSV data loading and tracked city storage
├── ui/                         # GUI panels (MainWindow, StatsPanel, etc.)
├── util/                       # Shared utilities (UIHelpers, StatsCalculator)
```

## ✅ Features

- View and track cities with real-time weather data
- Switch between Celsius and Fahrenheit
- Display historical statistics:
  - Highest/Lowest average temperature
  - Coldest city in January 2025
  - Most humid city in May 2025
  - Windiest city in April 2025
- Persistent tracked city list (`tracked_cities.txt`)
- Data source: `weather_data.csv`

## 👨‍💻 Design Patterns

- **MVC**: Clean separation between data, UI, and control logic
- **Observer**: GUI updates automatically when data changes
- **GRASP**: Controller, Creator, Low Coupling
- **SOLID**: Modular and maintainable class responsibilities

## 🚀 Getting Started

To run the app:
1. Open the project in VS Code or your preferred IDE.
2. Build and run `RealtimeWeatherApp.java` from the `app/` package.
3. Make sure `weather_data.csv` and `tracked_cities.txt` are present in the root.

## ℹ Notes

This version includes:
- Cleaned utility structure (no duplicate UIUtils or UIConstants)
- Ready-to-run Java code
- Complete feature implementation according to the assignment

Enjoy!

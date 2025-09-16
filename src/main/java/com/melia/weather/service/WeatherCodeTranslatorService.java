package com.melia.weather.service;

import com.melia.weather.dto.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherCodeTranslatorService {

    public Integer extractWeatherCode(WeatherResponse weatherInfo) {
        if (weatherInfo.dailyInformation() == null
                || weatherInfo.dailyInformation().weatherCode() == null
                || weatherInfo.dailyInformation().weatherCode().isEmpty()) {
            return null;
        }
        return weatherInfo.dailyInformation().weatherCode().getFirst();
    }

    public String getDescriptionFromWmoCode(WeatherResponse weatherInfo) {
        Integer code = extractWeatherCode(weatherInfo);
        if (code == null) return "Weather description not available";
        return switch (code) {
            case 0 -> "Clear sky";
            case 1, 2, 3 -> "Mainly clear, partly cloudy, and overcast";
            case 45, 48 -> "Fog and depositing rime fog";
            case 51, 53, 55 -> "Drizzle: Light, moderate, and dense intensity";
            case 56, 57 -> "Freezing Drizzle: Light and dense intensity";
            case 61, 63, 65 -> "Rain: Slight, moderate and heavy intensity";
            case 66, 67 -> "Freezing Rain: Light and heavy intensity";
            case 71, 73, 75 -> "Snow fall: Slight, moderate, and heavy intensity";
            case 77 -> "Snow grains";
            case 80, 81, 82 -> "Rain showers: Slight, moderate, and violent";
            case 85, 86 -> "Snow showers slight and heavy";
            case 95 -> "Thunderstorm: Slight or moderate";
            case 96, 99 -> "Thunderstorm with slight and heavy hail";
            default -> "Weather description not available";
        };
    }

}


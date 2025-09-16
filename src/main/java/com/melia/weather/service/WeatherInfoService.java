package com.melia.weather.service;

import com.melia.weather.client.OpenMeteoClient;
import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import com.melia.weather.exceptions.WeatherDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherInfoService {

    private final OpenMeteoClient openMeteoClient;
    private final WeatherCodeTranslatorService weatherCodeTranslatorService;

    public WeatherResponse getWeatherInfoForSpecificDate(WeatherRequest request) {
        WeatherResponse weatherInfo = openMeteoClient.getWeatherResponse(request);
        return addDescriptionToWeather(weatherInfo);
    }

    private WeatherResponse addDescriptionToWeather(WeatherResponse weatherInfo) {
        if (weatherInfo == null) {
            throw new WeatherDataNotFoundException("No weather data available for the requested date/location");
        }
        String description = weatherCodeTranslatorService.getDescriptionFromWmoCode(weatherInfo);

        return new WeatherResponse(
                weatherInfo.latitude(),
                weatherInfo.longitude(),
                weatherInfo.timeZone(),
                weatherInfo.dailyInformation(),
                weatherInfo.dailyUnits(),
                description
        );
    }

}
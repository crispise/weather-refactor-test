package com.melia.weather.service;

import com.melia.weather.client.OpenMeteoClient;
import com.melia.weather.dto.DailyInformationResponse;
import com.melia.weather.dto.DailyInformationUnits;
import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import com.melia.weather.exceptions.WeatherDataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherInfoServiceTest {

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private WeatherCodeTranslatorService weatherCodeTranslatorService;

    @InjectMocks
    private WeatherInfoService weatherInfoService;


    @Test
    void returns_description_whenCode() {
        WeatherRequest request = new WeatherRequest(51.54, 22.0 , null);
        WeatherResponse response = new WeatherResponse(
                51.539066, 22.011063, "Europe/Warsaw",
                new DailyInformationResponse(List.of("2025-09-17"), List.of( 51), List.of( 16.1), List.of( 10.0),
                        List.of( 14.0), List.of(8.5), List.of(45093.3), List.of( 8828.88)),
                new DailyInformationUnits(null,null,null,null,null,null,null),
                null
        );

        when(openMeteoClient.getWeatherResponse(request)).thenReturn(response);
        when(weatherCodeTranslatorService.getDescriptionFromWmoCode(response)).thenReturn("Drizzle: Light, moderate, and dense intensity");
        WeatherResponse out = weatherInfoService.getWeatherInfoForSpecificDate(new WeatherRequest(51.54, 22.0, null));

        assertEquals("Drizzle: Light, moderate, and dense intensity", out.weatherDescription());
    }

    @Test
    void throws_notFound_when_client_returnsNull() {
        WeatherRequest request = new WeatherRequest(40.0, -3.7, null);
        when(openMeteoClient.getWeatherResponse(request)).thenReturn(null);
        assertThrows(WeatherDataNotFoundException.class,
                () -> weatherInfoService.getWeatherInfoForSpecificDate(request));
    }

}





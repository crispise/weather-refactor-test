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
        WeatherRequest request = new WeatherRequest(40.0, -3.7, null);
        WeatherResponse response = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(List.of("2025-09-16"), List.of(0), List.of(), List.of(),
                        List.of(), List.of(), List.of(), List.of()),
                new DailyInformationUnits(null,null,null,null,null,null,null),
                null
        );

        when(openMeteoClient.getWeatherResponse(request)).thenReturn(response);
        when(weatherCodeTranslatorService.getDescriptionFromWmoCode(response)).thenReturn("Clear sky");
        WeatherResponse out = weatherInfoService.getWeatherInfoForSpecificDate(new WeatherRequest(40.0, -3.7, null));

        assertEquals("Clear sky", out.weatherDescription());

    }

    @Test
    void throws_notFound_when_client_returnsNull() {
        WeatherRequest request = new WeatherRequest(40.0, -3.7, null);
        when(openMeteoClient.getWeatherResponse(request)).thenReturn(null);
        assertThrows(WeatherDataNotFoundException.class,
                () -> weatherInfoService.getWeatherInfoForSpecificDate(request));
    }

}





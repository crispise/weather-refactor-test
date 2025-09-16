package com.melia.weather.service;

import com.melia.weather.dto.DailyInformationResponse;
import com.melia.weather.dto.DailyInformationUnits;
import com.melia.weather.dto.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherCodeTranslatorServiceTest {

    @InjectMocks
    private WeatherCodeTranslatorService weatherCodeTranslatorService; // instancia real

    @Test
    void returns_null_whenNoCode() {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(
                        List.of("2025-09-16"),
                        List.of(),
                        List.of(), List.of(), List.of(), List.of(), List.of(), List.of()
                ),
                new DailyInformationUnits(null,null,null,null,null,null,null),
                null
        );

        Integer code = weatherCodeTranslatorService.extractWeatherCode(weatherInfo);

        assertNull(code);
    }

}

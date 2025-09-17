package com.melia.weather.service;

import com.melia.weather.dto.DailyInformationResponse;
import com.melia.weather.dto.DailyInformationUnits;
import com.melia.weather.dto.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class WeatherCodeTranslatorServiceTest {

    @InjectMocks
    private WeatherCodeTranslatorService weatherCodeTranslatorService;

    @Test
    void returns_null_whenNo_dailyInformation() {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                null,
                new DailyInformationUnits(null, null, null, null, null, null, null),
                null
        );

        Integer code = weatherCodeTranslatorService.extractWeatherCode(weatherInfo);

        assertNull(code);
    }

    @Test
    void returns_null_whenCode_is_null() {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(
                        List.of("2025-09-16"),
                        null,
                        List.of(), List.of(), List.of(), List.of(), List.of(), List.of()
                ),
                new DailyInformationUnits(null, null, null, null, null, null, null),
                null
        );

        Integer code = weatherCodeTranslatorService.extractWeatherCode(weatherInfo);

        assertNull(code);
    }

    @Test
    void returns_null_whenCode_is_empty() {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(
                        List.of("2025-09-16"),
                        List.of(),
                        List.of(), List.of(), List.of(), List.of(), List.of(), List.of()
                ),
                new DailyInformationUnits(null, null, null, null, null, null, null),
                null
        );

        Integer code = weatherCodeTranslatorService.extractWeatherCode(weatherInfo);

        assertNull(code);
    }

    @Test
    void returns_code() {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(
                        List.of("2025-09-16"),
                        List.of(2),
                        List.of(), List.of(), List.of(), List.of(), List.of(), List.of()
                ),
                new DailyInformationUnits(null, null, null, null, null, null, null),
                null
        );

        Integer code = weatherCodeTranslatorService.extractWeatherCode(weatherInfo);

        assertEquals(2, code);
    }

    @ParameterizedTest(name = "code={0} -> \"{1}\"")
    @CsvSource(
            delimiter = ';',
            nullValues = "NULL",
            textBlock = """
                        NULL; Weather description not available
                        0;    Clear sky
                        1;    Mainly clear, partly cloudy, and overcast
                        2;    Mainly clear, partly cloudy, and overcast
                        3;    Mainly clear, partly cloudy, and overcast
                        45;   Fog and depositing rime fog
                        48;   Fog and depositing rime fog
                        51;   Drizzle: Light, moderate, and dense intensity
                        53;   Drizzle: Light, moderate, and dense intensity
                        55;   Drizzle: Light, moderate, and dense intensity
                        56;   Freezing Drizzle: Light and dense intensity
                        57;   Freezing Drizzle: Light and dense intensity
                        61;   Rain: Slight, moderate and heavy intensity
                        63;   Rain: Slight, moderate and heavy intensity
                        65;   Rain: Slight, moderate and heavy intensity
                        66;   Freezing Rain: Light and heavy intensity
                        67;   Freezing Rain: Light and heavy intensity
                        71;   Snow fall: Slight, moderate, and heavy intensity
                        73;   Snow fall: Slight, moderate, and heavy intensity
                        75;   Snow fall: Slight, moderate, and heavy intensity
                        77;   Snow grains
                        80;   Rain showers: Slight, moderate, and violent
                        81;   Rain showers: Slight, moderate, and violent
                        82;   Rain showers: Slight, moderate, and violent
                        85;   Snow showers slight and heavy
                        86;   Snow showers slight and heavy
                        95;   Thunderstorm: Slight or moderate
                        96;   Thunderstorm with slight and heavy hail
                        99;   Thunderstorm with slight and heavy hail
                        4645; Weather description not available
                    """
    )
    void returns_expected_description_for_code(Integer code, String expectedDescription) {
        WeatherResponse weatherInfo = new WeatherResponse(
                40.0, -3.7, "Europe/Madrid",
                new DailyInformationResponse(
                        List.of("2025-09-16"),
                        code == null ? null : List.of(code),
                        List.of(), List.of(), List.of(), List.of(), List.of(), List.of()
                ),
                new DailyInformationUnits(null, null, null, null, null, null, null),
                null
        );
        String description = weatherCodeTranslatorService.getDescriptionFromWmoCode(weatherInfo);
        assertEquals(expectedDescription.trim(), description);
    }


}

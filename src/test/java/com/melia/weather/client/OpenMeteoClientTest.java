package com.melia.weather.client;

import com.melia.weather.dto.DailyInformationResponse;
import com.melia.weather.dto.DailyInformationUnits;
import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import com.melia.weather.exceptions.ExternalApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenMeteoClientTest {


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    RestClient rest;

    OpenMeteoClient client;

    @BeforeEach
    void setUp() {
        client = new OpenMeteoClient("http://dummy"); // o null
        org.springframework.test.util.ReflectionTestUtils.setField(client, "rest", rest);
    }

    @Test
    void getWeatherResponse_returns_weatherResponse_with_date_request() {
        WeatherRequest request = new WeatherRequest(51.54, 22.0, LocalDate.of(2025, 9, 17));

        WeatherResponse expectedResponse = new WeatherResponse(
                51.539066, 22.011063, "Europe/Warsaw",
                new DailyInformationResponse(
                        List.of("2025-09-17"),
                        List.of(51),
                        List.of(16.1),
                        List.of(10.0),
                        List.of(14.0),
                        List.of(8.5),
                        List.of(45093.3),
                        List.of(8828.88)
                ),
                new DailyInformationUnits("wmo code", "°C", "°C", "°C", "°C", "s", "s"),
                "Drizzle: Light, moderate, and dense intensity"
        );

        URI expectedUri = UriComponentsBuilder
                .fromPath("/v1/forecast")
                .queryParam("latitude", request.lat())
                .queryParam("longitude", request.lon())
                .queryParam("daily", "weather_code", "temperature_2m_max", "temperature_2m_min",
                        "apparent_temperature_max", "apparent_temperature_min",
                        "daylight_duration", "sunshine_duration")
                .queryParam("timezone", "auto")
                .queryParam("start_date", request.date())
                .queryParam("end_date", request.date())
                .build()
                .toUri();

        when(rest.get()
                .uri(expectedUri)
                .retrieve()
                .body(WeatherResponse.class))
                .thenReturn(expectedResponse);

        WeatherResponse out = client.getWeatherResponse(request);

        assertEquals(expectedResponse, out);
    }

    @Test
    void test_getWeatherResponse_HttpMessageConversionException() {
        WeatherRequest request = new WeatherRequest(51.54, 22.0, LocalDate.of(2025, 9, 17));

        URI expectedUri = UriComponentsBuilder
                .fromPath("/v1/forecast")
                .queryParam("latitude", request.lat())
                .queryParam("longitude", request.lon())
                .queryParam("daily", "weather_code", "temperature_2m_max", "temperature_2m_min",
                        "apparent_temperature_max", "apparent_temperature_min",
                        "daylight_duration", "sunshine_duration")
                .queryParam("timezone", "auto")
                .queryParam("start_date", request.date())
                .queryParam("end_date", request.date())
                .build()
                .toUri();

        when(rest.get().uri((expectedUri)).retrieve().body(WeatherResponse.class))
                .thenThrow(new HttpMessageConversionException("bad JSON"));

        ExternalApiException ex = assertThrows(ExternalApiException.class,
                () -> client.getWeatherResponse(request));

        assertTrue(ex.getMessage().startsWith("Failed to parse provider response"));
    }

    @Test
    void test_getWeatherResponse_throw_RestClientException() {
        WeatherRequest request = new WeatherRequest(51.54, 22.0, LocalDate.of(2025, 9, 17));

        URI expectedUri = UriComponentsBuilder
                .fromPath("/v1/forecast")
                .queryParam("latitude", request.lat())
                .queryParam("longitude", request.lon())
                .queryParam("daily", "weather_code", "temperature_2m_max", "temperature_2m_min",
                        "apparent_temperature_max", "apparent_temperature_min",
                        "daylight_duration", "sunshine_duration")
                .queryParam("timezone", "auto")
                .queryParam("start_date", request.date())
                .queryParam("end_date", request.date())
                .build()
                .toUri();

        when(rest.get().uri((expectedUri)).retrieve().body(WeatherResponse.class))
                .thenThrow(new RestClientException("open meteo error"));

        ExternalApiException ex = assertThrows(ExternalApiException.class, () -> client.getWeatherResponse(request));

        assertTrue(ex.getMessage().startsWith("Error calling weather provider"));
    }

}
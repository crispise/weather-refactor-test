package com.melia.weather.client;

import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Component
public class OpenMeteoClient {

    private final RestClient rest;

    public OpenMeteoClient(
            @Value("${open.meteo.base.url}") String baseUrl
    ) {
        this.rest = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public WeatherResponse getWeatherResponse(WeatherRequest request) {
        LocalDate date = request.date() == null ? LocalDate.now() : request.date();
        URI uri = UriComponentsBuilder
                .fromPath("/v1/forecast")
                .queryParam("latitude", request.lat())
                .queryParam("longitude", request.lon())
                .queryParam("daily", "weather_code", "temperature_2m_max", "temperature_2m_min", "apparent_temperature_max", "apparent_temperature_min", "daylight_duration", "sunshine_duration")
                .queryParam("timezone", "auto")
                .queryParam("start_date", date)
                .queryParam("end_date", date)
                .build()
                .toUri();

        return rest.get()
                .uri(uri)
                .retrieve()
                .body(WeatherResponse.class);

    }
}
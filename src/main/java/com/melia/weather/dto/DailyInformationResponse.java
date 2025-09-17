package com.melia.weather.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DailyInformationResponse(
        List<String> time,
        @JsonAlias("weather_code") List<Integer> weatherCode,
        @JsonAlias("temperature_2m_max") List<Double> temperature2mMax,
        @JsonAlias("temperature_2m_min") List<Double> temperature2mMin,
        @JsonAlias("apparent_temperature_max") List<Double> apparentTemperatureMax,
        @JsonAlias("apparent_temperature_min") List<Double> apparentTemperatureMin,
        @JsonAlias("daylight_duration") List<Double> daylightDuration,
        @JsonAlias("sunshine_duration") List<Double> sunshineDuration
) {
}


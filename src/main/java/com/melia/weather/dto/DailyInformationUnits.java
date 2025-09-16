package com.melia.weather.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DailyInformationUnits(
        @JsonAlias("weather_code") String weatherCode,
        @JsonAlias("temperature_2m_max") String temperature2mMax,
        @JsonAlias("temperature_2m_min") String temperature2mMin,
        @JsonAlias("apparent_temperature_max") String apparentTemperatureMax,
        @JsonAlias("apparent_temperature_min") String apparentTemperatureMin,
        @JsonAlias("daylight_duration") String daylightDuration,
        @JsonAlias("sunshine_duration") String sunshineDuration
) {}


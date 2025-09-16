package com.melia.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(double latitude,
                              double longitude,
                              @JsonProperty ("timezone") String timeZone,
                              @JsonProperty ("daily") DailyInformationResponse dailyInformation,
                              @JsonProperty("daily_units") DailyInformationUnits dailyUnits,
                              String weatherDescription) {
}
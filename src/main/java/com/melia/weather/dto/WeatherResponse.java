package com.melia.weather.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record WeatherResponse(Double latitude,
                              Double longitude,
                              @JsonAlias("timezone") String timeZone,
                              @JsonAlias("daily") DailyInformationResponse dailyInformation,
                              @JsonAlias("daily_units") DailyInformationUnits dailyUnits,
                              String weatherDescription) {
}
package com.melia.weather.dto;

public record DailyInformationUnits(String weather_code,
                                    String temperature_2m_max,
                                    String temperature_2m_min,
                                    String apparent_temperature_max,
                                    String apparent_temperature_min,
                                    String daylight_duration,
                                    String sunshine_duration) {
}

package com.melia.weather.dto;

import java.util.List;

public record DailyInformationResponse(List<String> time,
                                       List<Integer> weather_code,
                                       List<Double>  temperature_2m_max,
                                       List<Double>  temperature_2m_min,
                                       List<Double>  apparent_temperature_max,
                                       List<Double>  apparent_temperature_min,
                                       List<Double>  daylight_duration,
                                       List<Double>  sunshine_duration) {
}

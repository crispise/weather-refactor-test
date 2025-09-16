package com.melia.weather.controller;

import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import com.melia.weather.service.WeatherInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WeatherController {

    private final WeatherInfoService weatherInformationService;

    @PostMapping("/weatherInformation")
    public WeatherResponse getWeatherInformationForSpecificDate(@Valid @RequestBody WeatherRequest request) {
        return weatherInformationService.getWeatherInfoForSpecificDate(request);
    }

}

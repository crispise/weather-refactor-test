package com.melia.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melia.weather.dto.DailyInformationResponse;
import com.melia.weather.dto.DailyInformationUnits;
import com.melia.weather.dto.WeatherRequest;
import com.melia.weather.dto.WeatherResponse;
import com.melia.weather.service.WeatherInfoService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WeatherControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockitoBean
    private WeatherInfoService weatherInformationService;

    @Test
    void postWeatherInformation_returnsResponseFromService() throws Exception {
        WeatherRequest request = new WeatherRequest(51.54, 22.0, null);
        WeatherResponse response = new WeatherResponse(
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

        when(weatherInformationService.getWeatherInfoForSpecificDate(request)).thenReturn(response);

        mockMvc.perform(post("/api/weatherInformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(51.539066))
                .andExpect(jsonPath("$.longitude").value(22.011063))
                .andExpect(jsonPath("$.timeZone").value("Europe/Warsaw"))
                .andExpect(jsonPath("$.weatherDescription").value("Drizzle: Light, moderate, and dense intensity"))
                .andExpect(jsonPath("$.dailyInformation.time[0]").value("2025-09-17"))
                .andExpect(jsonPath("$.dailyInformation.weatherCode[0]").value(51))
                .andExpect(jsonPath("$.dailyInformation.temperature2mMax[0]").value(16.1))
                .andExpect(jsonPath("$.dailyUnits.weatherCode").value("wmo code"))
                .andExpect(jsonPath("$.dailyUnits.temperature2mMax").value("°C"));
    }
}


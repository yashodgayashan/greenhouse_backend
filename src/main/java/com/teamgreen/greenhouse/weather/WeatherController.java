package com.teamgreen.greenhouse.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${weather.url}")
    private String remoteUrl;

    @Value("${api.key}")
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping("/current")
    public ResponseEntity getCurrentWeather() {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(remoteUrl + "/current.json?key=" + apiKey
                        + "&q=7.269014,79.889694&aqi=no" , HttpMethod.GET, requestEntity, String.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }


}

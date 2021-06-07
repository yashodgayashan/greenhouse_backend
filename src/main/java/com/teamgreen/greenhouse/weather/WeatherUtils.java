package com.teamgreen.greenhouse.weather;

import com.teamgreen.greenhouse.dao.WeatherSummaryData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeatherUtils {

    RestTemplate restTemplate;
    private String remoteUrl;
    private String apiKey;

    public WeatherUtils(RestTemplate restTemplate, String remoteUrl, String apiKey) {
        this.restTemplate = restTemplate;
        this.remoteUrl = remoteUrl;
        this.apiKey = apiKey;
    }

    private static final Logger logger = LoggerFactory.getLogger(WeatherUtils.class);

    public List<WeatherSummaryData> getWeatherPredictions(double latitude, double longitude) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(remoteUrl + "/forecast.json?key=" + apiKey
                + "&q=" + latitude + "," + longitude + "&days=10&aqi=no" , HttpMethod.GET, requestEntity, String.class);
        return getWeatherPredictedData(response.getBody());
    }

    public WeatherSummaryData getWeatherAverage(List<WeatherSummaryData> weatherSummaryData) {
        WeatherSummaryData summaryData = new WeatherSummaryData();
        double minTemp = 0.0;
        double maxTemp = 0.0;

        for (WeatherSummaryData data : weatherSummaryData) {
            System.out.println(data.getMinTemp());
            minTemp += data.getMinTemp();
            maxTemp += data.getMaxTemp();
        }

        minTemp /= weatherSummaryData.size();
        maxTemp /= weatherSummaryData.size();

        summaryData.setMinTemp(minTemp);
        summaryData.setMaxTemp(maxTemp);

        return summaryData;
    }

    List<WeatherSummaryData> getWeatherPredictedData(String jsonString) {

        List<WeatherSummaryData> weatherDataList = new ArrayList<>();

        JSONArray forecastDay = (JSONArray) (new JSONObject((new JSONObject(jsonString)).get("forecast").toString())).get("forecastday");
        Iterator<Object> forecastDays = forecastDay.iterator();

        while(forecastDays.hasNext()){
            WeatherSummaryData data = new WeatherSummaryData();
            Object uJson = forecastDays.next();
            JSONObject uj = (JSONObject) uJson;
            JSONObject day = new JSONObject(uj.get("day").toString());
            data.setDate(uj.getString("date"));
            data.setMaxTemp(day.getDouble("maxtemp_c"));
            data.setMinTemp(day.getDouble("mintemp_c"));
            weatherDataList.add(data);
        }
        return weatherDataList;
    }
}

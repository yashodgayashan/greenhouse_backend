package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.Greenhouse;
import com.teamgreen.greenhouse.dao.Location;
import com.teamgreen.greenhouse.dao.SuitablePlant;
import com.teamgreen.greenhouse.dao.WeatherSummaryData;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.locations.LocationDbHandler;
import com.teamgreen.greenhouse.weather.WeatherUtils;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseUtils {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    GreenhousesDbHandler greenhousesDbHandler;
    LocationDbHandler locationDbHandler;
    WeatherUtils weatherUtils;

    public GreenhouseUtils(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedParamJdbc, RestTemplate restTemplate, String remoteUrl, String apiKey) {
        this.jdbcTemplate = jdbc;
        this.namedParameterJdbcTemplate = namedParamJdbc;
        this.greenhousesDbHandler = new GreenhousesDbHandler(jdbc, namedParamJdbc);
        this.locationDbHandler =  new LocationDbHandler(jdbc, namedParamJdbc);
        this.weatherUtils = new WeatherUtils(restTemplate, remoteUrl, apiKey);
    }

    public WeatherSummaryData getSuitablePlants(long greenhouseId) throws CustomException, JSONException, ParseException {

        Location location = this.locationDbHandler.getLocation(this.greenhousesDbHandler.getGreenhouse(greenhouseId).getLocationId());
        List<WeatherSummaryData> weatherPredictions = this.weatherUtils.getWeatherPredictions(location.getLatitude(), location.getLongatude());
        WeatherSummaryData weatherSummaryData = this.weatherUtils.getWeatherAverage(weatherPredictions);

        return weatherSummaryData;
        // Get Plant List
        // Select Plant Using weather parameters.
        // Get number of plants
        // get min and max expected harvest
//        List<SuitablePlant> suitablePlants = new ArrayList<>();
//        return suitablePlants;
    }

    public List<Data> getData(long greenhouseId) {
    	// get nodes for the greenhouse
    	// get node sensors for nodes
    	// get data for those node sensors
    	//return
    }

}

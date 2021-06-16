package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.*;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.locations.LocationDbHandler;
import com.teamgreen.greenhouse.plant.PlantInfoDbHandler;
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
    PlantInfoDbHandler plantInfoDbHandler;

    public GreenhouseUtils(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedParamJdbc, RestTemplate restTemplate, String remoteUrl, String apiKey) {
        this.jdbcTemplate = jdbc;
        this.namedParameterJdbcTemplate = namedParamJdbc;
        this.greenhousesDbHandler = new GreenhousesDbHandler(jdbc, namedParamJdbc);
        this.locationDbHandler =  new LocationDbHandler(jdbc, namedParamJdbc);
        this.plantInfoDbHandler = new PlantInfoDbHandler(jdbc, namedParamJdbc);
        this.weatherUtils = new WeatherUtils(restTemplate, remoteUrl, apiKey);
    }

    public List<SuitablePlant> getSuitablePlants(long greenhouseId) throws CustomException, JSONException, ParseException {

        Greenhouse greenhouse = this.greenhousesDbHandler.getGreenhouse(greenhouseId);
        Location location = this.locationDbHandler.getLocation(greenhouse.getLocationId());
        List<WeatherSummaryData> weatherPredictions = this.weatherUtils.getWeatherPredictions(location.getLatitude(), location.getLongatude());
        WeatherSummaryData weatherSummaryData = this.weatherUtils.getWeatherAverage(weatherPredictions);

        List<PlantInfo> plantInfo = this.plantInfoDbHandler.getPlantInfo();

        List<SuitablePlant> suitablePlants = new ArrayList<>();
        for (PlantInfo plant : plantInfo) {
//            if(plant.getMaxTemperatureLow() == plant.getMaxTemperatureHigh() &&
//                    !(weatherSummaryData.getMaxTemp() < plant.getMaxTemperatureHigh() + 1
//                        && weatherSummaryData.getMaxTemp() > plant.getMaxTemperatureHigh() - 1)) {
//                continue;
//            } else if (plant.getMinTemperatureLow() == plant.getMinTemperatureHigh() &&
//                    !(weatherSummaryData.getMinTemp() < plant.getMinTemperatureHigh() + 1
//                            && weatherSummaryData.getMinTemp() > plant.getMinTemperatureHigh() - 1)) {
//                continue;
//            } else if (plant.getMaxTemperatureLow() != plant.getMaxTemperatureHigh() &&
//                    !(weatherSummaryData.getMaxTemp() < plant.getMaxTemperatureHigh() + 1
//                            && weatherSummaryData.getMaxTemp() > plant.getMaxTemperatureHigh() - 1)) {
//                continue;
//            } else if (plant.getMinTemperatureLow() != plant.getMinTemperatureHigh() &&
//                    !(weatherSummaryData.getMinTemp() < plant.getMinTemperatureHigh() + 1
//                            && weatherSummaryData.getMinTemp() > plant.getMinTemperatureHigh() - 1)) {
//                continue;
//            } else {
                SuitablePlant suitablePlant = new SuitablePlant();
                suitablePlant.setPlantId((int) plant.getId());
                suitablePlant.setPlantName(plant.getName());
                suitablePlant.setSpecies(plant.getSpecies());
                suitablePlant.setNoOfPlants(getNumberOfPlants(plant.getSpacing(), greenhouse.getWidth(),
                        greenhouse.getLength(), plant.getPlantsPerPot()));
                suitablePlant.setExpectedMinHarvest(suitablePlant.getNoOfPlants() * plant.getMinNoOfHarvest());
                suitablePlant.setExpectedMaxHarvest(suitablePlant.getNoOfPlants() * plant.getMaxNumberOfHarvest());
                suitablePlants.add(suitablePlant);
//            }
        }
        return suitablePlants;
    }

    int getNumberOfPlants(double plantSpace, double greenhouseWidth, double greenhouseLength, int numberOfPlants) {
        // need to implement and algorithm
        return 1000;
    }
}

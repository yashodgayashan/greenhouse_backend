package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.*;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.locations.LocationDbHandler;
import com.teamgreen.greenhouse.plant.PlantInfoDbHandler;
import com.teamgreen.greenhouse.data.DataDbHandler;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.locations.LocationDbHandler;
import com.teamgreen.greenhouse.nodeSensors.NodeSensorDbHandler;
import com.teamgreen.greenhouse.nodes.NodeDbHandler;
import com.teamgreen.greenhouse.sensors.SensorDbHandler;
import com.teamgreen.greenhouse.weather.WeatherUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GreenhouseUtils {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    GreenhousesDbHandler greenhousesDbHandler;
    LocationDbHandler locationDbHandler;
    WeatherUtils weatherUtils;
    PlantInfoDbHandler plantInfoDbHandler;
    NodeDbHandler nodeDbHandler;
    NodeSensorDbHandler nodeSensorDbHandler;
    DataDbHandler dataDbHandler;
    SensorDbHandler sensorDbHandler;

    public GreenhouseUtils(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedParamJdbc, RestTemplate restTemplate, String remoteUrl, String apiKey) {
        this.jdbcTemplate = jdbc;
        this.namedParameterJdbcTemplate = namedParamJdbc;
        this.greenhousesDbHandler = new GreenhousesDbHandler(jdbc, namedParamJdbc);
        this.locationDbHandler =  new LocationDbHandler(jdbc, namedParamJdbc);
        this.plantInfoDbHandler = new PlantInfoDbHandler(jdbc, namedParamJdbc);
        this.weatherUtils = new WeatherUtils(restTemplate, remoteUrl, apiKey);
        this.nodeDbHandler = new NodeDbHandler(jdbc, namedParamJdbc);
        this.nodeSensorDbHandler = new NodeSensorDbHandler(jdbc, namedParamJdbc);
        this.dataDbHandler = new DataDbHandler(jdbc, namedParamJdbc);
        this.sensorDbHandler = new SensorDbHandler(jdbc, namedParamJdbc);
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
    public List<FormattedData> getGreenhouseData(long greenhouseId, Date startDate, Date endDate) {
        List<LocalDate> dates = getDatesBetweenTwoDates(startDate, endDate);
        List<FormattedData> greenhouseData = new ArrayList<>();
        List<Node> nodes = this.nodeDbHandler.getNodesByGreenhouseId(greenhouseId);
        for (Node node : nodes) {
            List<FormattedData.NodeData> nodeDataList = new ArrayList<>();
            List<NodeSensor> tempNodeSensorList = this.nodeSensorDbHandler.getNodeSensorsByNodeId(node.getId());
            for (NodeSensor nodeSensor : tempNodeSensorList) {
                long sensorId = 0;
                String sensorName = "";
                try {
                    Sensor sensor = this.sensorDbHandler.getSensor(nodeSensor.getSensorId());
                    sensorId = sensor.getId();
                    sensorName = sensor.getName();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                List<Data> tempDataList = this.dataDbHandler.getDataByNodeSensorId(nodeSensor.getId(), startDate, endDate);
                List<Double> dataList = new ArrayList<>();
                List<Timestamp> dateList = new ArrayList<>();
                for (Data data : tempDataList) {
                    dataList.add(data.getData());
                    dateList.add(data.getCreatedAt());
                }

                FormattedData.NodeData nodeData = new FormattedData.NodeData(sensorId, sensorName, sensorName, dataList, dateList);
                nodeDataList.add(nodeData);
            }

            FormattedData formattedDataObject = new FormattedData(node.getId(), nodeDataList);
            greenhouseData.add(formattedDataObject);
        }
        return greenhouseData;
    }

    public List<LocalDate> getDatesBetweenTwoDates(Date startDate, Date endDate) {
        LocalDate startingDate = new java.sql.Date(startDate.getTime()).toLocalDate();
        LocalDate endingDate = new java.sql.Date(endDate.getTime()).toLocalDate();
        long numberOfDaysBetween = ChronoUnit.DAYS.between(startingDate, endingDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numberOfDaysBetween)
                .mapToObj(i -> startingDate.plusDays(i))
                .collect(Collectors.toList());
    }
}

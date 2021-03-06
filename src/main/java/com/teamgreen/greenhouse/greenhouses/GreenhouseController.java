package com.teamgreen.greenhouse.greenhouses;

import com.teamgreen.greenhouse.dao.*;
import com.teamgreen.greenhouse.dao.Data;
import com.teamgreen.greenhouse.dao.FormattedData;
import com.teamgreen.greenhouse.dao.Greenhouse;
import com.teamgreen.greenhouse.dao.Node;
import com.teamgreen.greenhouse.dao.search.dao.GreenhouseSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.greenhouses.crop.CropDbHandler;
import com.teamgreen.greenhouse.greenhouses.greenhousePlant.GreenhousePlantDbHandler;
import com.teamgreen.greenhouse.greenhouses.plant.PlantDbHandler;
import com.teamgreen.greenhouse.greenhouses.plantDisease.PlanDiseaseDbHandler;
import com.teamgreen.greenhouse.greenhouses.plantHarvest.PlantHarvestDbHandler;
import com.teamgreen.greenhouse.utils.DbUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.sql.Date;
import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.greenhouses.Constants.GREENHOUSES_TABLE;

@RestController
@RequestMapping("/greenhouses")
public class GreenhouseController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;
    @Autowired
    RestTemplate restTemplate;

    @Value("${weather.url}")
    private String remoteUrl;

    @Value("${api.key}")
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(GreenhouseController.class);
    private GreenhousesDbHandler handler;
    private GreenhousePlantDbHandler greenhousePlantDbHandler;
    private PlantDbHandler plantDbHandler;
    private PlanDiseaseDbHandler planDiseaseDbHandler;
    private CropDbHandler cropDbHandler;
    private PlantHarvestDbHandler plantHarvestDbHandler;
    private GreenhouseUtils greenhouseUtils;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new GreenhousesDbHandler(this.jdbc, this.namedParamJdbc);
        greenhousePlantDbHandler = new GreenhousePlantDbHandler(this.jdbc, this.namedParamJdbc);
        plantDbHandler = new PlantDbHandler(this.jdbc, this.namedParamJdbc);
        planDiseaseDbHandler = new PlanDiseaseDbHandler(this.jdbc, this.namedParamJdbc);
        plantHarvestDbHandler = new PlantHarvestDbHandler(this.jdbc, this.namedParamJdbc);
        cropDbHandler = new CropDbHandler(this.jdbc, this.namedParamJdbc);
        greenhouseUtils = new GreenhouseUtils(this.jdbc, this.namedParamJdbc, this.restTemplate,
                this.remoteUrl, this.apiKey);
    }

    @GetMapping("")
    public ResponseEntity getGreenhouses() {
        return new ResponseEntity(handler.getGreenhouses(), HttpStatus.OK);
    }

    @Deprecated
    @GetMapping("/id")
    public ResponseEntity getGreenhouseId() throws MysqlHandlerException {
        DbUtils dbUtils = new DbUtils(this.jdbc);
        long id = dbUtils.getLastIdFromTable(GREENHOUSES_TABLE) + 1;
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/{greenhouse-id}")
    public ResponseEntity getGreenhouse(@PathVariable("greenhouse-id") long greenhouseId) {
        try {
            return new ResponseEntity(handler.getGreenhouse(greenhouseId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{greenhouse-id}/suitability")
    public ResponseEntity getSuitablePlantsForGreenhouse(@PathVariable("greenhouse-id") long greenhouseId) {
        try {
            return new ResponseEntity(greenhouseUtils.getSuitablePlants(greenhouseId), HttpStatus.OK);
        } catch (CustomException | JSONException | ParseException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createGreenhouse(@RequestBody Greenhouse greenhouse) throws MysqlHandlerException {
        int status = handler.addGreenhouse(greenhouse);
            if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(GREENHOUSES_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted greenhouse failed, {}", greenhouse);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{greenhouse-id}")
    public ResponseEntity updateLocation(@PathVariable("greenhouse-id") long greenhouseId, @RequestBody Greenhouse greenhouse) {
        int status = handler.updateGreenhouse(greenhouseId, greenhouse);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating greenhouse failed, {}", greenhouse);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{greenhouse-id}")
    public ResponseEntity deleteLocation(@PathVariable("greenhouse-id") long greenhouseId) {
        int status = handler.deleteGreenhouse(greenhouseId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("deleting greenhouse failed, {}", greenhouseId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchLocations(@RequestBody GreenhouseSearchDao searchDao) {
        List<Greenhouse> greenhouses;
        try {
            greenhouses = handler.searchGreenhouses(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(greenhouses, HttpStatus.OK);
    }

    @GetMapping("/{greenhouse-id}/data")
    public ResponseEntity getGreenhouseData(@PathVariable("greenhouse-id") long greenhouseId, @RequestParam Date startDate, @RequestParam Date endDate) {
        List<FormattedData> greenhouseData;
        try {
            greenhouseData = greenhouseUtils.getGreenhouseData(greenhouseId, startDate, endDate);
        } catch (Exception e) {
            logger.error("error occurred while getting greenhouse data\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(greenhouseData, HttpStatus.OK);
    }

    @GetMapping("/{greenhouse-id}/plant_diseases")
    public ResponseEntity getGreenhousePlantDisease(@PathVariable("greenhouse-id") long greenhouseId) {
         try {
            return new ResponseEntity<>(greenhousePlantDbHandler.getGreenhouseDiseases(greenhouseId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error occurred while getting greenhouse data\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{greenhouse-id}/greenhouse-plants")
    public ResponseEntity getGreenhousePlants(@PathVariable("greenhouse-id") long greenhouseId) {
        try {
            return new ResponseEntity(greenhousePlantDbHandler.getGreenhousePlants(greenhouseId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{greenhouse-id}/greenhouse-plants")
    public ResponseEntity createGreenhousePlants(@PathVariable("greenhouse-id") long greenhouseId, @RequestBody GreenhousePlants greenhousePlants) {
        try {
            if (greenhousePlantDbHandler.hasActivePlant(greenhouseId)) {
                return new ResponseEntity("Has Active plant", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(greenhousePlantDbHandler.addPlant(greenhousePlants), HttpStatus.OK);
            }
        } catch (JSONException | MysqlHandlerException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}")
    public ResponseEntity updateGreenhousePlants(
            @PathVariable("greenhouse-id") long greenhouseId,
            @PathVariable("greenhouse-plant-id") long greenhousePlantId,
            @RequestBody GreenhousePlants greenhousePlants) {
        try {
            return new ResponseEntity(greenhousePlantDbHandler.updateGreenhousePlant(greenhousePlantId, greenhousePlants.getEndedAt(),
                    greenhousePlants.isCompleted()), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}")
    public ResponseEntity getAllPlants(@PathVariable("greenhouse-id") long greenhouseId,
                                       @PathVariable("greenhouse-plant-id") long greenhousePlantId) {
        try {
            return new ResponseEntity(plantDbHandler.getPlants(greenhousePlantId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/diseases")
    public ResponseEntity getAllPlantDiseases(@PathVariable("greenhouse-id") long greenhouseId,
                                              @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                              @PathVariable("plant-id") long plantId) {
        try {
            return new ResponseEntity(planDiseaseDbHandler.getPlantDiseases(plantId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/diseases")
    public ResponseEntity createPlantDiseases(@PathVariable("greenhouse-id") long greenhouseId,
                                              @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                              @PathVariable("plant-id") long plantId,
                                              @RequestBody PlantDisease plantDisease) {
        try {
            System.out.println("Hello");
            return new ResponseEntity(planDiseaseDbHandler.addPlantDisease(plantDisease), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/diseases/{plant-diseaseId}")
    public ResponseEntity updatePlantDiseases(@PathVariable("greenhouse-id") long greenhouseId, @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                              @PathVariable("plant-diseaseId") long plantDiseaseId, @RequestBody PlantDisease plantDisease) {
        try {
            return new ResponseEntity(planDiseaseDbHandler.updatePlantDisease(plantDiseaseId, plantDisease), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Plant Harvest
    @GetMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/harvest")
    public ResponseEntity getAllPlantHarvest(@PathVariable("greenhouse-id") long greenhouseId,
                                              @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                              @PathVariable("plant-id") long plantId) {
        try {
            return new ResponseEntity(plantHarvestDbHandler.getPlantHarvest(plantId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/harvests")
    public ResponseEntity createPlantHarvest(@PathVariable("greenhouse-id") long greenhouseId,
                                              @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                              @PathVariable("plant-id") long plantId,
                                              @RequestBody PlantHarvest plantHarvest) {
        try {
            return new ResponseEntity(plantHarvestDbHandler.addPlantHarvest(plantHarvest), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Crop
    @GetMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/crops")
    public ResponseEntity getAllPlantCrops(@PathVariable("greenhouse-id") long greenhouseId,
                                             @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                             @PathVariable("plant-id") long plantId) {
        try {
            return new ResponseEntity(cropDbHandler.getCrops(plantId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/crops")
    public ResponseEntity createCrop(@PathVariable("greenhouse-id") long greenhouseId,
                                             @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                             @PathVariable("plant-id") long plantId,
                                             @RequestBody Crop crop) {
        try {
            return new ResponseEntity(cropDbHandler.createCrop(crop), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/crops/{crop-id}")
    public ResponseEntity getAllPlantCropInfo(@PathVariable("greenhouse-id") long greenhouseId,
                                           @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                           @PathVariable("plant-id") long plantId,
                                              @PathVariable("crop-id") long cropId) {
        try {
            return new ResponseEntity(cropDbHandler.getCropInfo(cropId), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{greenhouse-id}/greenhouse-plants/{greenhouse-plant-id}/plants/{plant-id}/crops/{crop-id}")
    public ResponseEntity createCropInfo(@PathVariable("greenhouse-id") long greenhouseId,
                                     @PathVariable("greenhouse-plant-id") long greenhousePlantId,
                                     @PathVariable("plant-id") long plantId, @PathVariable("crop-id") long cropId,
                                     @RequestBody CropInfo cropInfo) {
        try {
            return new ResponseEntity(cropDbHandler.createCropInfo(cropInfo), HttpStatus.OK);
        } catch (JSONException  e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

package com.teamgreen.greenhouse.plant;

import com.teamgreen.greenhouse.dao.PlantInfo;
import com.teamgreen.greenhouse.dao.search.dao.PlantInfoSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.utils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_REMOVED;
import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_UPDATED;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.plant.Constants.PLANT_INFO_TABLE;

@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);
    private PlantInfoDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new PlantInfoDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("/info")
    public ResponseEntity getPlantInfo() {
        return new ResponseEntity(handler.getPlantInfo(), HttpStatus.OK);
    }

    @GetMapping("/info/{plant-id}")
    public ResponseEntity getPlantInfo(@PathVariable("plant-id") long plantId) {
        try {
            return new ResponseEntity(handler.getPlanInfo(plantId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/info")
    public ResponseEntity createPlantInfor(@RequestBody PlantInfo plantInfo) throws MysqlHandlerException {
        int status = handler.addPlantInfo(plantInfo);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(PLANT_INFO_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted plant info failed, {}", plantInfo);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/info/{plant-id}")
    public ResponseEntity updatePlantInfo(@PathVariable("plant-id") long plantId, @RequestBody PlantInfo plantInfo) {
        int status = handler.updatePlantInfo(plantId, plantInfo);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating plant info failed, {}", plantInfo);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/info/{plant-id}}")
    public ResponseEntity deletePlantInfo(@PathVariable("plant-id") long plantId) {
        int status = handler.deletePlantInfo(plantId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating plant info failed, {}", plantId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/info/search")
    public ResponseEntity searchPlantInfo(@RequestBody PlantInfoSearchDao searchDao) {
        List<PlantInfo> plantInfos;
        try {
            plantInfos = handler.searchPlantInfo(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching plantInfo\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(plantInfos, HttpStatus.OK);
    }
}

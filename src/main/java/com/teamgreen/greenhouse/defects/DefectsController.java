package com.teamgreen.greenhouse.defects;

import com.teamgreen.greenhouse.dao.Disease;
import com.teamgreen.greenhouse.dao.search.dao.DiseaseSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.exceptions.MysqlHandlerException;
import com.teamgreen.greenhouse.locations.LocationController;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_UPDATED;
import static com.teamgreen.greenhouse.constants.Constants.SUCCESSFULLY_REMOVED;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.defects.Constants.DISEASE_TABLE;

@RestController
@RequestMapping("/defects")
public class DefectsController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(DefectsController.class);
    private DefectsDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new DefectsDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getDefects() {
        return new ResponseEntity(handler.getDiseases(), HttpStatus.OK);
    }

    @GetMapping("/{defect-id}")
    public ResponseEntity getDefect(@PathVariable("defect-id") long defectId, @RequestParam Optional<String> name) {
        try {
            if (name.isPresent()) {
                return new ResponseEntity(handler.getDisease(name.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity(handler.getDisease(defectId), HttpStatus.OK);
            }
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createDefect(@RequestBody Disease disease) throws MysqlHandlerException {
        int status = handler.addDisease(disease);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(DISEASE_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted disease failed, {}", disease);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{defect-id}")
    public ResponseEntity updateDisease(@PathVariable("defect-id") long defectId, @RequestBody Disease disease) {
        int status = handler.updateDisease(defectId, disease);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating disease failed, {}", disease);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{defect-id}")
    public ResponseEntity deleteDisease(@PathVariable("defect-id") long defectId) {
        int status = handler.deleteDisease(defectId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating disease failed, {}", defectId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchDefects(@RequestBody DiseaseSearchDao searchDao) {
        List<Disease> diseases;
        try {
            diseases = handler.searchDiseases(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching diseases\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(diseases, HttpStatus.OK);

    }
}

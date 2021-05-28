package com.teamgreen.greenhouse.defects.solution;

import com.teamgreen.greenhouse.dao.DiseaseSolution;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.util.Optional;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.defects.Constants.*;

@RestController
@RequestMapping("/defect-solutions")
public class SolutionController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);
    private SolutionDbHandler handler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new SolutionDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getDefectSolutions(@RequestParam Optional<Long> diseaseId) {
        if (diseaseId.isPresent()) {
            return new ResponseEntity(handler.getDiseaseSolutions(diseaseId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity(handler.getDiseaseSolutions(), HttpStatus.OK);
        }
    }

    @GetMapping("/{solution-id}")
    public ResponseEntity getDefectSolution(@PathVariable("solution-id") long defectSolutionId) {
        try {
            return new ResponseEntity(handler.getDiseaseSolution(defectSolutionId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createDefectSolution(@RequestBody DiseaseSolution diseaseSolution) throws MysqlHandlerException {
        int status = handler.addDiseaseSolution(diseaseSolution);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(DISEASE_MEDICINES_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted disease solution failed, {}", diseaseSolution);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{solution-id}")
    public ResponseEntity updateDefectSolution(@PathVariable("solution-id") long defectSolutionId,
                                               @RequestBody DiseaseSolution diseaseSolution) {
        int status = handler.updateDiseaseSolution(defectSolutionId, diseaseSolution);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating disease solution failed, {}", diseaseSolution);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{solution-id}")
    public ResponseEntity deleteDefectSolution(@PathVariable("solution-id") long defectSolutionId) {
        int status = handler.deleteDiseaseSolution(defectSolutionId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("deleting disease solution failed, {}", defectSolutionId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

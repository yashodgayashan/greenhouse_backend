package com.teamgreen.greenhouse.nodes;

import com.teamgreen.greenhouse.dao.Data;
import com.teamgreen.greenhouse.dao.Node;
import com.teamgreen.greenhouse.dao.search.dao.NodeSearchDao;
import com.teamgreen.greenhouse.data.DataDbHandler;
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

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.*;
import static com.teamgreen.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.teamgreen.greenhouse.nodes.Constants.NODES_TABLE;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbc;

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);
    private NodeDbHandler handler;
    private DataDbHandler dataDbHandler;

    @PostConstruct
    void setJdbcHandlers() {
        handler = new NodeDbHandler(this.jdbc, this.namedParamJdbc);
        dataDbHandler = new DataDbHandler(this.jdbc, this.namedParamJdbc);
    }

    @GetMapping("")
    public ResponseEntity getNodes() {
        return new ResponseEntity(handler.getNodes(), HttpStatus.OK);
    }

    @GetMapping("/{node-id}")
    public ResponseEntity getNode(@PathVariable("node-id") long nodeId) {
        try {
            return new ResponseEntity(handler.getNode(nodeId), HttpStatus.OK);
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createNode(@RequestBody Node node) throws MysqlHandlerException {
        int status = handler.addNode(node);
        if (status > 0) {
            DbUtils dbUtils = new DbUtils(this.jdbc);
            long id = dbUtils.getLastIdFromTable(NODES_TABLE);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            logger.error("inserted node failed, {}", node);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{node-id}")
    public ResponseEntity postData(@RequestBody List<Data> data) {
        data.stream().forEach(dataVal -> dataDbHandler.addData(dataVal));
        return new ResponseEntity<>(SUCCESSFULLY_INSERTED, HttpStatus.OK);
    }

    @PutMapping("/{node-id}")
    public ResponseEntity updateSensor(@PathVariable("node-id") long nodeId, @RequestBody Node node) {
        int status = handler.updateNode(nodeId, node);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_UPDATED, HttpStatus.OK);
        } else {
            logger.error("updating node failed, {}", node);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{node-id}")
    public ResponseEntity deleteSensor(@PathVariable("node-id") long nodeId) {
        int status = handler.deleteNode(nodeId);
        if (status > 0) {
            return new ResponseEntity<>(SUCCESSFULLY_REMOVED, HttpStatus.OK);
        } else {
            logger.error("updating node failed, {}", nodeId);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity searchNodes(@RequestBody NodeSearchDao searchDao) {
        List<Node> nodes;
        try {
            nodes = handler.searchNodes(searchDao);
        } catch (CustomException e) {
            logger.error("error occurred while searching splits\n" + e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }
}

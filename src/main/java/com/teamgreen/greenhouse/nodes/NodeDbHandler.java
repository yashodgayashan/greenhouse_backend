package com.teamgreen.greenhouse.nodes;

import com.teamgreen.greenhouse.dao.Node;
import com.teamgreen.greenhouse.dao.mappers.NodeMapper;
import com.teamgreen.greenhouse.dao.search.dao.NodeSearchDao;
import com.teamgreen.greenhouse.exceptions.CustomException;
import com.teamgreen.greenhouse.store.DbHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.nodes.Constants.NODES_TABLE;
import static com.teamgreen.greenhouse.nodes.Constants.NODE_ID;
import static com.teamgreen.greenhouse.nodes.Constants.NODE_GREENHOUSE_ID;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.*;
import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.getUpdateSyntaxFinal;

public class NodeDbHandler extends DbHandler {

    public NodeDbHandler(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    List<Node> getNodes() {
        final String query = "SELECT * FROM " + NODES_TABLE + " ORDER BY " + NODE_ID + " DESC";
        return this.jdbcTemplate().query(query, new NodeMapper());
    }

    Node getNode(long id) throws CustomException {
        try {
            final String query = "SELECT * FROM " + NODES_TABLE + " WHERE " + NODE_ID + " = ?";
            return this.jdbcTemplate().queryForObject(query, new NodeMapper(), id);
        } catch (Exception e) {
            throw new CustomException("There is no node found with id: " + id);
        }
    }

    int addNode(Node node)  {
        final String insertQuery =
                "INSERT INTO " + NODES_TABLE + " ("  + encapFieldWithBackTick(NODE_GREENHOUSE_ID) + ") VALUES "
                        + getStatementParams(1);

        return this.jdbcTemplate().update(insertQuery, node.getGreenhouseId());
    }

    int updateNode(long id, Node node)  {
        final String updateQuery =
                "UPDATE " + NODES_TABLE + " SET " + getUpdateSyntaxFinal(NODE_GREENHOUSE_ID) + " WHERE id = ?";
        return this.jdbcTemplate().update(updateQuery, node.getGreenhouseId(), id);
    }

    int deleteNode(long id) {
        final String deleteQuery =
                "UPDATE " + NODES_TABLE + " SET " + getUpdateSyntaxFinal(IS_DISABLED) + " WHERE id = ?";
        return this.jdbcTemplate().update(deleteQuery, 1, id);
    }

    List<Node> searchNodes(NodeSearchDao searchDao) throws CustomException {
        return this.namedJdbcTemplate().query(
                searchDao.query(true), searchDao.namedParameterMap(), new NodeMapper());
    }
}

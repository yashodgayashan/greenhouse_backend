package com.teamgreen.greenhouse.dao.mappers;

import com.teamgreen.greenhouse.dao.Node;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.teamgreen.greenhouse.constants.Constants.IS_DISABLED;
import static com.teamgreen.greenhouse.constants.Constants.CREATED_AT;
import static com.teamgreen.greenhouse.constants.Constants.MODIFIED_AT;
import static com.teamgreen.greenhouse.nodes.Constants.NODE_ID;
import static com.teamgreen.greenhouse.nodes.Constants.NODE_GREENHOUSE_ID;

public class NodeMapper implements RowMapper<Node> {

    @Override
    public Node mapRow(ResultSet rs, int i) throws SQLException {
        Node node = new Node();
        node.setId(rs.getLong(NODE_ID));
        node.setGreenhouseId(rs.getLong(NODE_GREENHOUSE_ID));
        node.setDisabled(rs.getBoolean(IS_DISABLED));
        node.setCreatedAt(rs.getTimestamp(CREATED_AT));
        node.setModifiedAt(rs.getTimestamp(MODIFIED_AT));
        return node;
    }
}

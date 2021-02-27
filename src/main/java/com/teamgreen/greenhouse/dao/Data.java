package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class Data {

    private long id;
    private long nodeSensorId;
    private double data;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getNodeSensorId() { return nodeSensorId; }

    public void setNodeSensorId(long nodeSensorId) { this.nodeSensorId = nodeSensorId; }

    public double getData() { return data; }

    public void setData(double data) { this.data = data; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

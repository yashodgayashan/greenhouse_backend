package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class NodeSensor {
    private long id;
    private long nodeId;
    private long sensorId;
    private double minValue;
    private double maxValue;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getNodeId() { return nodeId; }

    public void setNodeId(long nodeId) { this.nodeId = nodeId; }

    public long getSensorId() { return sensorId; }

    public void setSensorId(long sensorId) { this.sensorId = sensorId; }

    public double getMinValue() { return minValue; }

    public void setMinValue(double minValue) { this.minValue = minValue; }

    public double getMaxValue() { return maxValue; }

    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

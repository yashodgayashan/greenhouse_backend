package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class PlantInfo {
    private long id;
    private String name;
    private String description;
    private double plantDuration;
    private double minTemperature;
    private double maxTemperature;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getPlantDuration() { return plantDuration; }

    public void setPlantDuration(double plantDuration) { this.plantDuration = plantDuration; }

    public double getMinTemperature() { return minTemperature; }

    public void setMinTemperature(double minTemperature) { this.minTemperature = minTemperature; }

    public double getMaxTemperature() { return maxTemperature; }

    public void setMaxTemperature(double maxTemperature) { this.maxTemperature = maxTemperature; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

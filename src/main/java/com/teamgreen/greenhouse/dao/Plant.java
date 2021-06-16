package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class Plant {
    private long id;
    private long plantId;
    private long greenhouseId;
    private long greenhousePlantId;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public Plant(long plantId, long greenhouseId, long greenhousePlantId) {
        this.plantId = plantId;
        this.greenhouseId = greenhouseId;
        this.greenhousePlantId = greenhousePlantId;
    }

    public Plant() { }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPlantId() { return plantId; }

    public void setPlantId(long plantId) { this.plantId = plantId; }

    public long getGreenhouseId() { return greenhouseId; }

    public void setGreenhouseId(long greenhouseId) { this.greenhouseId = greenhouseId; }

    public long getGreenhousePlantId() { return greenhousePlantId; }

    public void setGreenhousePlantId(long greenhousePlantId) { this.greenhousePlantId = greenhousePlantId; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

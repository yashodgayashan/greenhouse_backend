package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class GreenhousePlants {

    private long id;
    private long plantId;
    private long greenhouseId;
    private int numberOfPlants;
    private boolean isCompleted;
    private boolean isDeleted;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPlantId() { return plantId; }

    public void setPlantId(long plantId) { this.plantId = plantId; }

    public long getGreenhouseId() { return greenhouseId; }

    public void setGreenhouseId(long greenhouseId) { this.greenhouseId = greenhouseId; }

    public int getNumberOfPlants() { return numberOfPlants; }

    public void setNumberOfPlants(int numberOfPlants) { this.numberOfPlants = numberOfPlants; }

    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) { isCompleted = completed; }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public Timestamp getStartedAt() { return startedAt; }

    public void setStartedAt(Timestamp startedAt) { this.startedAt = startedAt; }

    public Timestamp getEndedAt() { return endedAt; }

    public void setEndedAt(Timestamp endedAt) { this.endedAt = endedAt; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

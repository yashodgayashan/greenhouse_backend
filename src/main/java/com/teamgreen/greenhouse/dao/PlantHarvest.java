package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class PlantHarvest {
    private long id;
    private long plantId;
    private long count;
    private Timestamp date;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPlantId() { return plantId; }

    public void setPlantId(long plantId) { this.plantId = plantId; }

    public long getCount() { return count; }

    public void setCount(long count) { this.count = count; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

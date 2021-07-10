package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class PlantDisease {
    private long id;
    private long plantId;
    private long plantInfoId;
    private long diseaseId;
    private long solutionId;
    private Timestamp appliedDate;
    private Timestamp resolvedDate;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPlantId() { return plantId; }

    public void setPlantId(long plantId) { this.plantId = plantId; }

    public long getPlantInfoId() {
        return plantInfoId;
    }

    public void setPlantInfoId(long plantInfoId) {
        this.plantInfoId = plantInfoId;
    }

    public long getDiseaseId() { return diseaseId; }

    public void setDiseaseId(long diseaseId) { this.diseaseId = diseaseId; }

    public long getSolutionId() { return solutionId; }

    public void setSolutionId(long solutionId) { this.solutionId = solutionId; }

    public Timestamp getAppliedDate() { return appliedDate; }

    public void setAppliedDate(Timestamp appliedDate) { this.appliedDate = appliedDate; }

    public Timestamp getResolvedDate() { return resolvedDate; }

    public void setResolvedDate(Timestamp resolvedDate) { this.resolvedDate = resolvedDate; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

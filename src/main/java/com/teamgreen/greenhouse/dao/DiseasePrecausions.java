package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class DiseasePrecausions {

    private long id;
    private String name;
    private String description;
    private long diseaseId;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public long getDiseaseId() { return diseaseId; }

    public void setDiseaseId(long diseaseId) { this.diseaseId = diseaseId; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

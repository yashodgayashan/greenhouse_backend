package com.teamgreen.greenhouse.dao;

import java.sql.Date;
import java.sql.Timestamp;

public class WaterSchedule {

    private long id;
    private long greenhouseId;
    private Date startDate;
    private Date endDate;
    private Timestamp wateringTime;
    private boolean isEnabled;
    private boolean isDeleted;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getGreenhouseId() { return greenhouseId; }

    public void setGreenhouseId(long greenhouseId) { this.greenhouseId = greenhouseId; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Timestamp getWateringTime() { return wateringTime; }

    public void setWateringTime(Timestamp wateringTime) { this.wateringTime = wateringTime; }

    public boolean isEnabled() { return isEnabled; }

    public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class CropInfo {

    private long id;
    private long cropId;
    private double length;
    private Timestamp date;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getCropId() { return cropId; }

    public void setCropId(long cropId) { this.cropId = cropId; }

    public double getLength() { return length; }

    public void setLength(double length) { this.length = length; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }
}



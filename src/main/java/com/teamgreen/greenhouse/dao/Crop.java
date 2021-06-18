package com.teamgreen.greenhouse.dao;

public class Crop {
    private long id;
    private long plantId;
    private double length;
    private double depth;
    private double height;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPlantId() { return plantId; }

    public void setPlantId(long plantId) { this.plantId = plantId; }

    public double getLength() { return length; }

    public void setLength(double length) { this.length = length; }

    public double getDepth() { return depth; }

    public void setDepth(double depth) { this.depth = depth; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }
}



package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class Greenhouse {

    private long id;
    private String name;
    private String location;
    private double length;
    private double width;
    private double height;
    private long locationId;
    private String imageURL;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public void setId(long id) { this.id = id; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public long getId() { return id; }

    public String getName() { return name; }

    public String getLocation() { return location; }

    public double getLength() { return length; }

    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }

    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }

    public long getLocationId() { return locationId; }

    public String getImageURL() { return imageURL; }

    public boolean isDisabled() { return isDisabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }

    public void setLocation(String location) { this.location = location; }

    public void setLocationId(long locationId) { this.locationId = locationId; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }
}

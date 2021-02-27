package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class Greenhouse {

    private long id;
    private String name;
    private String location;
    private long locationId;
    private String imageURL;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public String getName() { return name; }

    public String getLocation() { return location; }

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

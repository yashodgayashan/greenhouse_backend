package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class Location {

    private long id;
    private String name;
    private String location;
    private String imageURL;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public void setId(long id) { this.id = id; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public long getId() { return id; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }
}

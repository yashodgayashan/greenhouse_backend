package com.teamgreen.greenhouse.dao;

import java.sql.Timestamp;

public class PlantInfo {
    private long id;
    private String name;
    private String species;
    private String description;
    private double plantDuration;
    private double minTemperatureLow;
    private double minTemperatureHigh;
    private double maxTemperatureLow;
    private double maxTemperatureHigh;
    private double spacing;
    private int plantsPerPot;
    private int minNoOfHarvest;
    private int maxNumberOfHarvest;
    private double averageWeightOfHarvest;
    private double stage1Duration;
    private double stage2Duration;
    private double stage3Duration;
    private double stage4Duration;
    private String solid;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }

    public void setSpecies(String species) { this.species = species; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getPlantDuration() { return plantDuration; }

    public void setPlantDuration(double plantDuration) { this.plantDuration = plantDuration; }

    public double getMinTemperatureLow() { return minTemperatureLow; }

    public void setMinTemperatureLow(double minTemperatureLow) { this.minTemperatureLow = minTemperatureLow; }

    public double getMinTemperatureHigh() { return minTemperatureHigh; }

    public void setMinTemperatureHigh(double minTemperatureHigh) { this.minTemperatureHigh = minTemperatureHigh; }

    public double getMaxTemperatureLow() { return maxTemperatureLow; }

    public void setMaxTemperatureLow(double maxTemperatureLow) { this.maxTemperatureLow = maxTemperatureLow; }

    public double getMaxTemperatureHigh() { return maxTemperatureHigh; }

    public void setMaxTemperatureHigh(double maxTemperatureHigh) { this.maxTemperatureHigh = maxTemperatureHigh; }

    public double getSpacing() { return spacing; }

    public void setSpacing(double spacing) { this.spacing = spacing; }

    public int getPlantsPerPot() { return plantsPerPot; }

    public void setPlantsPerPot(int plantsPerPot) { this.plantsPerPot = plantsPerPot; }

    public int getMinNoOfHarvest() { return minNoOfHarvest; }

    public void setMinNoOfHarvest(int minNoOfHarvest) { this.minNoOfHarvest = minNoOfHarvest; }

    public int getMaxNumberOfHarvest() { return maxNumberOfHarvest; }

    public void setMaxNumberOfHarvest(int maxNumberOfHarvest) { this.maxNumberOfHarvest = maxNumberOfHarvest; }

    public double getAverageWeightOfHarvest() { return averageWeightOfHarvest; }

    public void setAverageWeightOfHarvest(double averageWeightOfHarvest) { this.averageWeightOfHarvest = averageWeightOfHarvest; }

    public double getStage1Duration() { return stage1Duration; }

    public void setStage1Duration(double stage1Duration) { this.stage1Duration = stage1Duration; }

    public double getStage2Duration() { return stage2Duration; }

    public void setStage2Duration(double stage2Duration) { this.stage2Duration = stage2Duration; }

    public double getStage3Duration() { return stage3Duration; }

    public void setStage3Duration(double stage3Duration) { this.stage3Duration = stage3Duration; }

    public double getStage4Duration() { return stage4Duration; }

    public void setStage4Duration(double stage4Duration) { this.stage4Duration = stage4Duration; }

    public String getSolid() { return solid; }

    public void setSolid(String solid) { this.solid = solid; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

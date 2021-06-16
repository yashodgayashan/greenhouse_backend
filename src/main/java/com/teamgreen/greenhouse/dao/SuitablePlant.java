package com.teamgreen.greenhouse.dao;

public class SuitablePlant {

    private int plantId;
    private int noOfPlants;
    private double expectedMinHarvest;
    private double expectedMaxHarvest;

    public int getPlantId() { return plantId; }

    public void setPlantId(int plantId) { this.plantId = plantId; }

    public int getNoOfPlants() { return noOfPlants; }

    public void setNoOfPlants(int noOfPlants) { this.noOfPlants = noOfPlants; }

    public double getExpectedMinHarvest() { return expectedMinHarvest; }

    public void setExpectedMinHarvest(double expectedMinHarvest) { this.expectedMinHarvest = expectedMinHarvest; }

    public double getExpectedMaxHarvest() { return expectedMaxHarvest; }

    public void setExpectedMaxHarvest(double expectedMaxHarvest) { this.expectedMaxHarvest = expectedMaxHarvest; }
}

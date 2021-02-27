package com.teamgreen.greenhouse.dao;

import com.teamgreen.greenhouse.enums.DataTypes;

import java.sql.Timestamp;

public class Sensor {
    private long id;
    private String name;
    private String description;
    private DataTypes dataType;
    private double minValue;
    private double maxValue;
    private String technology;
    private double workingVoltage;
    private String dimensions;
    private String specialFacts;
    private boolean isDisabled;
    private Timestamp modifiedAt;
    private Timestamp createdAt;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDataType() {
        if(this.dataType == DataTypes.DOUBLE) {
            return "Double";
        } else {
            return "Integer";
        }
    }

    public void setDataType(String dataType) {
        if (dataType.equals("Double")) {
            this.dataType = DataTypes.DOUBLE;
        } else {
            this.dataType = DataTypes.INTEGER;
        }
    }

    public void setDataType(DataTypes dataType) { this.dataType = dataType; }

    public double getMinValue() { return minValue; }

    public void setMinValue(double minValue) { this.minValue = minValue; }

    public double getMaxValue() { return maxValue; }

    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }

    public String getTechnology() { return technology; }

    public void setTechnology(String technology) { this.technology = technology; }

    public double getWorkingVoltage() { return workingVoltage; }

    public void setWorkingVoltage(double workingVoltage) { this.workingVoltage = workingVoltage; }

    public String getDimensions() { return dimensions; }

    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getSpecialFacts() { return specialFacts; }

    public void setSpecialFacts(String specialFacts) { this.specialFacts = specialFacts; }

    public boolean isDisabled() { return isDisabled; }

    public void setDisabled(boolean disabled) { isDisabled = disabled; }

    public Timestamp getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

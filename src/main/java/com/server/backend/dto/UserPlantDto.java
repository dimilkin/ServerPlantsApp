package com.server.backend.dto;

public class UserPlantDto {
    private int id;
    private int waterPeriod;
    private String providedName;
    private int plantModelId;

    public UserPlantDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaterPeriod() {
        return waterPeriod;
    }

    public void setWaterPeriod(int waterPeriod) {
        this.waterPeriod = waterPeriod;
    }

    public String getProvidedName() {
        return providedName;
    }

    public void setProvidedName(String providedName) {
        this.providedName = providedName;
    }

    public int getPlantModelId() {
        return plantModelId;
    }

    public void setPlantModelId(int plantModelId) {
        this.plantModelId = plantModelId;
    }
}

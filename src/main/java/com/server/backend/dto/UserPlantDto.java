package com.server.backend.dto;

public class UserPlantDto {
    private int userPlantId;
    private int waterPeriod;
    private String providedName;
    private int plantModelId;

    public UserPlantDto() {
    }

    public int getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(int userPlantId) {
        this.userPlantId = userPlantId;
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

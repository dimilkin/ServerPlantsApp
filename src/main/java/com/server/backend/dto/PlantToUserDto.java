package com.server.backend.dto;

public class PlantToUserDto {
    private int waterPeriod;
    private String providedName;

    public PlantToUserDto() {
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
}

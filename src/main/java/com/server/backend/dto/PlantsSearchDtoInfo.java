package com.server.backend.dto;

public class PlantsSearchDtoInfo {

    private int id;
    private String commonNames;
    private String scientificNames;

    public PlantsSearchDtoInfo() {
    }

    public PlantsSearchDtoInfo(int id, String commonNames, String scientificNames) {
        this.id = id;
        this.commonNames = commonNames;
        this.scientificNames = scientificNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }

    public String getScientificNames() {
        return scientificNames;
    }

    public void setScientificNames(String scientificNames) {
        this.scientificNames = scientificNames;
    }
}

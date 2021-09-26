package com.server.backend.dto;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PotentialPlantProblems;

import java.util.List;

public class PlantInfoDto {

    private int id;
    private String origin;
    private String commonName;
    private String scientificName;
    private String maxGrowth;
    private String poisonousForPets;
    private String temperature;
    private String light;
    private String watering;
    private String soil;
    private String rePotting;
    private String airHumidity;
    private String propagation;
    private String whereItGrowsBest;
    private int plantOwnerId;
    private int assinedUserId;
    private List<PotentialPlantProblems> potentialProblems;
    private List<AdditionalPlantInfo> additionalInformation;

}

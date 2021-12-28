package com.server.backend.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plants")
public class PlantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "origin")
    private String origin;
    @Column(name = "common_name")
    private String commonName;
    @Column(name = "scientific_name")
    private String scientificName;
    @Column(name = "max_growth")
    private String maxGrowth;
    @Column(name = "poisonous_pets")
    private String poisonousForPets;
    @Column(name = "temperature")
    private String temperature;
    @Column(name = "light")
    private String light;
    @Column(name = "watering")
    private String watering;
    @Column(name = "soil")
    private String soil;
    @Column(name = "potting")
    private String rePotting;
    @Column(name = "humidity")
    private String airHumidity;
    @Column(name = "propagation")
    private String propagation;
    @Column(name = "grow_best")
    private String whereItGrowsBest;

    @OneToMany(mappedBy = "plantId")
    private List<PotentialPlantProblems> potentialProblems;
    @OneToMany(mappedBy = "plantId")
    private List<AdditionalPlantInfo> additionalInformation;

    public PlantModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantModel that = (PlantModel) o;
        return getId() == that.getId() &&
                Objects.equals(getCommonName(), that.getCommonName()) &&
                Objects.equals(getScientificName(), that.getScientificName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCommonName(), getScientificName());
    }

    public int getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getMaxGrowth() {
        return maxGrowth;
    }

    public String getPoisonousForPets() {
        return poisonousForPets;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getLight() {
        return light;
    }

    public String getWatering() {
        return watering;
    }

    public String getSoil() {
        return soil;
    }

    public String getRePotting() {
        return rePotting;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public String getPropagation() {
        return propagation;
    }

    public String getWhereItGrowsBest() {
        return whereItGrowsBest;
    }

    public List<PotentialPlantProblems> getPotentialProblems() {
        return potentialProblems;
    }

    public List<AdditionalPlantInfo> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public void setMaxGrowth(String maxGrowth) {
        this.maxGrowth = maxGrowth;
    }

    public void setPoisonousForPets(String poisonousForPets) {
        this.poisonousForPets = poisonousForPets;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public void setRePotting(String rePotting) {
        this.rePotting = rePotting;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public void setPropagation(String propagation) {
        this.propagation = propagation;
    }

    public void setWhereItGrowsBest(String whereItGrowsBest) {
        this.whereItGrowsBest = whereItGrowsBest;
    }

    public void setPotentialProblems(List<PotentialPlantProblems> potentialProblems) {
        this.potentialProblems = potentialProblems;
    }

    public void setAdditionalInformation(List<AdditionalPlantInfo> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void addAdditionalInfo(AdditionalPlantInfo info){
        if (additionalInformation == null){
            additionalInformation = new ArrayList<>();
        }
        additionalInformation.add(info);
    }

    public void addPotentialProblem(PotentialPlantProblems info){
        if (potentialProblems == null){
            potentialProblems = new ArrayList<>();
        }
        potentialProblems.add(info);
    }


}

package com.server.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user_plants")
public class UserPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "water_period")
    private int waterPeriod;

    @Column(name = "provided_name")
    private String providedName;

    @JsonIgnoreProperties({"origin", "commonName","scientificName","maxGrowth","poisonousForPets",
            "temperature","light","watering","soil","rePotting","airHumidity","propagation","whereItGrowsBest",
            "potentialProblems","additionalInformation"})
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private PlantModel plant;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_owner")
    private UserInfo userOwner;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_assigned")
    private UserInfo userAssigned;

    public UserPlant() {
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

    public PlantModel getPlant() {
        return plant;
    }

    public void setPlant(PlantModel plant) {
        this.plant = plant;
    }

    public UserInfo getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserInfo userOwner) {
        this.userOwner = userOwner;
    }

    public UserInfo getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(UserInfo uaserAssigned) {
        this.userAssigned = uaserAssigned;
    }
}

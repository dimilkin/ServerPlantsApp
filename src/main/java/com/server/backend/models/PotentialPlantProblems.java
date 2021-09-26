package com.server.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "plant_problems")
public class PotentialPlantProblems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "problem_title")
    private String title;

    @Column(name = "problem_content")
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private PlantModel plantId;

    public PotentialPlantProblems() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PlantModel getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantModel plantId) {
        this.plantId = plantId;
    }
}

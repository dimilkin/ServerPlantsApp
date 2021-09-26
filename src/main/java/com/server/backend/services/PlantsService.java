package com.server.backend.services;

import com.server.backend.models.PlantModel;

import java.util.List;

public interface PlantsService {

    List<PlantModel> getAll();

    void insertAllToDb(List<PlantModel> plants);

    void create(PlantModel plant);

    void update(PlantModel plant);

    void delete(int id);

    PlantModel getById(int id);

    void addPlantToUserCollection(int userId, int plantId);

    void removePlantFromUserCollection(int userId, int plantId);
}

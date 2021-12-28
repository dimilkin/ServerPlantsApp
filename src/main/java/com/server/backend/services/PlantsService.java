package com.server.backend.services;

import com.server.backend.dto.PlantInfoDto;
import com.server.backend.models.PlantModel;
import java.util.List;

public interface PlantsService {

    List<PlantModel> getAll();

    void insertAllToDb(List<PlantModel> plants);

    void create(PlantModel plant);

    void update(PlantInfoDto plant, int plantId);

    void delete(int id);

    PlantModel getById(int id);
}

package com.server.backend.repos;

import com.server.backend.models.PlantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantsRepo extends JpaRepository<PlantModel, Integer> {

}

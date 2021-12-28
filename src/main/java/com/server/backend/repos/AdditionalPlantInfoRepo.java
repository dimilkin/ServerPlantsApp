package com.server.backend.repos;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalPlantInfoRepo extends JpaRepository<AdditionalPlantInfo, Integer> {
}

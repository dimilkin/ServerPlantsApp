package com.server.backend.repos;

import com.server.backend.models.Token;
import com.server.backend.models.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserPlantRepo extends JpaRepository<UserPlant, Integer> {
    @Query("SELECT userPlant FROM UserPlant userPlant WHERE userPlant.userOwner.id = ?1 AND userPlant.plant.id = ?2")
    UserPlant findByUserIdAndPlantId(int userId, int plantId);

    @Query("SELECT userPlant FROM UserPlant userPlant WHERE userPlant.userAssigned.id = ?1 AND userPlant.plant.id = ?2")
    UserPlant findByAssignerUserIdAndPlantId(int userId, int plantId);
}

package com.server.backend.repos;

import com.server.backend.models.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlantRepo extends JpaRepository<UserPlant, Integer> {
}

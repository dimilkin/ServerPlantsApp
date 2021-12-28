package com.server.backend.repos;

import com.server.backend.models.PotentialPlantProblems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotentialPlantProblemsRepo extends JpaRepository<PotentialPlantProblems, Integer> {
}

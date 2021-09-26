package com.server.backend.repos;

import com.server.backend.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRolesRepo extends JpaRepository<UserRole, Long> {

    @Query("SELECT role FROM UserRole role WHERE role.authority like %?1")
    public UserRole getByValue(String value);
}

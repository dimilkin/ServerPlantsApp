package com.server.backend.services.impl;

import com.server.backend.models.UserRole;
import com.server.backend.repos.UserRolesRepo;
import com.server.backend.services.UserRolesService;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl implements UserRolesService {

    private UserRolesRepo repository;

    public UserRolesServiceImpl(UserRolesRepo repository) {
        this.repository = repository;
    }

    @Override
    public void create(UserRole role) {
        repository.save(role);
    }

    @Override
    public UserRole getByValue(String value) {
        return repository.getByValue(value);
    }
}

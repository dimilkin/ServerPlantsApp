package com.server.backend.services;

import com.server.backend.models.UserRole;

public interface UserRolesService {

    void create(UserRole role);

    UserRole getByValue(String value);

}

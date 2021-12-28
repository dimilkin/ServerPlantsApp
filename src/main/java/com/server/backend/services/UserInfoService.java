package com.server.backend.services;

import com.server.backend.models.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getAll();

    void create(UserInfo user);

    void update(UserInfo user);

    void delete(int id);

    UserInfo getById(int id);

    UserInfo getByEmail(String userEmail);
}

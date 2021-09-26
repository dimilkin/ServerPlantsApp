package com.server.backend.services.impl;

import com.server.backend.models.UserInfo;
import com.server.backend.repos.UserInfoRepo;
import com.server.backend.services.UserInfoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepo repository;

    public UserInfoServiceImpl(UserInfoRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<UserInfo> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(UserInfo user) {
        repository.save(user);
    }

    @Override
    public void update(UserInfo user) {
        repository.save(user);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public UserInfo getById(int id) {
        return repository.findById(id).get();
    }

    public UserInfo getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public boolean emailAlreadyExists(String userEmail) {
        ExampleMatcher MAIL_MATCHER = ExampleMatcher.matching().withMatcher(userEmail, ExampleMatcher.GenericPropertyMatchers.ignoreCase());
        Example<UserInfo> example = Example.<UserInfo>of(new UserInfo(), MAIL_MATCHER);
        return repository.exists(example);
    }
}

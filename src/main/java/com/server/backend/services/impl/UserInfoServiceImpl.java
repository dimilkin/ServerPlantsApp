package com.server.backend.services.impl;

import com.server.backend.exceptions.DuplicateEntityException;
import com.server.backend.models.UserInfo;
import com.server.backend.repos.UserInfoRepo;
import com.server.backend.services.UserInfoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        if (emailAlreadyExists(user.getEmail())) {
            throw new DuplicateEntityException("User with such e-mail already exists");
        }
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
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new EntityNotFoundException("No user with that ID exists in DB");
        }
    }

    @Override
    public UserInfo getByEmail(String email) {
        if (emailAlreadyExists(email)) {
            return repository.getByEmail(email);
        }
        throw new EntityNotFoundException("No user found with that email");
    }

    private boolean emailAlreadyExists(String userEmail) {
        UserInfo user = new UserInfo();
        user.setEmail(userEmail);

        ExampleMatcher MAIL_MATCHER = ExampleMatcher.matchingAny().withMatcher(userEmail, ExampleMatcher.GenericPropertyMatchers.ignoreCase());
        Example<UserInfo> example = Example.<UserInfo>of(user, MAIL_MATCHER);
        return repository.exists(example);
    }
}

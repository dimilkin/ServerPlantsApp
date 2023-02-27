package com.server.backend.services.impl;

import com.server.backend.exceptions.DuplicateEntityException;
import com.server.backend.models.UserInfo;
import com.server.backend.repos.UserInfoRepo;
import com.server.backend.services.UserInfoService;
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
            throw new DuplicateEntityException("User with such e-mail already exists : " + user.getEmail());
        }
        repository.save(user);
    }

    @Override
    public void update(UserInfo user) {
        repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public UserInfo getById(int id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new EntityNotFoundException("No user with ID exists in DB : " + id);
        }
    }

    @Override
    public UserInfo getByEmail(String email) {
        if (emailAlreadyExists(email)) {
            return repository.getByEmail(email);
        }
        throw new EntityNotFoundException("No user found with email : " + email);
    }

    @Override
    public boolean emailAlreadyExists(String userEmail) {
        try {
            return repository.getByEmail(userEmail) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

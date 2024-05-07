package com.chandankrr.scm.service;

import com.chandankrr.scm.entity.User;
import com.chandankrr.scm.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User updatedUser) throws ResourceNotFoundException;

    void deleteUser(String id) throws ResourceNotFoundException;

    boolean isUserExist(String id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

}

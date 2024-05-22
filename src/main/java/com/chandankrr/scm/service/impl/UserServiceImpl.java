package com.chandankrr.scm.service.impl;

import com.chandankrr.scm.entity.User;
import com.chandankrr.scm.exception.ResourceNotFoundException;
import com.chandankrr.scm.helpers.AppConstants;
import com.chandankrr.scm.repository.UserRepository;
import com.chandankrr.scm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        // generate user id
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set user role
        user.setRoles(List.of(AppConstants.ROLE_USER));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User updatedUser) throws ResourceNotFoundException {
        User user = userRepository.findById(updatedUser.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setAbout(updatedUser.getAbout());
        user.setProfilePic(updatedUser.getProfilePic());
        user.setEnabled(updatedUser.isEnabled());
        user.setEmailVerified(updatedUser.isEmailVerified());
        user.setPhoneVerified(updatedUser.isPhoneVerified());
        user.setProvider(updatedUser.getProvider());
        user.setProviderUserId(updatedUser.getProviderUserId());

        User savedUser = userRepository.save(user);
        return Optional.of(savedUser);
    }

    @Override
    public void deleteUser(String id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public boolean isUserExist(String id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

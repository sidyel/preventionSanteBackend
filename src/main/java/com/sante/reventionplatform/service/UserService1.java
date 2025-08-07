package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService1 {

    @Autowired
    private UserRepository1 userRepository1;

    public List<User1> getAllUsers() {
        return userRepository1.findAll();
    }

    public Optional<User1> getUserById(Long id) {
        return userRepository1.findById(id);
    }

    public Optional<User1> getUserByEmail(String email) {
        return userRepository1.findByEmail(email);
    }

    public User1 saveUser(User1 user) {
        if (userRepository1.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        return userRepository1.save(user);
    }

    public User1 updateUser(Long id, User1 userDetails) {
        Optional<User1> userOpt = userRepository1.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        User1 user = userOpt.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());

        return userRepository1.save(user);
    }

    public void deleteUser(Long id) {
        userRepository1.deleteById(id);
    }
}
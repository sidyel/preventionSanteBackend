package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByTypeActeur(TypeActeur typeActeur) {
        return userRepository.findByTypeActeur(typeActeur);
    }

    public List<User> findByRegion(String region) {
        return userRepository.findByRegion(region);
    }

    public List<User> findByCommune(String commune) {
        return userRepository.findByCommune(commune);
    }

    public List<User> findByVillage(String village) {
        return userRepository.findByVillage(village);
    }

    public List<User> findActiveUsers() {
        return userRepository.findByActifTrue();
    }

    public List<User> findByTypeActeurAndRegion(TypeActeur typeActeur, String region) {
        return userRepository.findByTypeActeurAndRegion(typeActeur, region);
    }

    public Long countByTypeActeur(TypeActeur typeActeur) {
        return userRepository.countByTypeActeurAndActifTrue(typeActeur);
    }

    public void deactivateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setActif(false);
            userRepository.save(user.get());
        }
    }

    public void activateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setActif(true);
            userRepository.save(user.get());
        }
    }
}
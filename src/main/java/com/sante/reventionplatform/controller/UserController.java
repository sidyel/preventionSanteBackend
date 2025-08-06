package com.sante.reventionplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{typeActeur}")
    public ResponseEntity<List<User>> getUsersByType(@PathVariable TypeActeur typeActeur) {
        return ResponseEntity.ok(userService.findByTypeActeur(typeActeur));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<User>> getUsersByRegion(@PathVariable String region) {
        return ResponseEntity.ok(userService.findByRegion(region));
    }

    @GetMapping("/commune/{commune}")
    public ResponseEntity<List<User>> getUsersByCommune(@PathVariable String commune) {
        return ResponseEntity.ok(userService.findByCommune(commune));
    }

    @GetMapping("/village/{village}")
    public ResponseEntity<List<User>> getUsersByVillage(@PathVariable String village) {
        return ResponseEntity.ok(userService.findByVillage(village));
    }

    @GetMapping("/actifs")
    public ResponseEntity<List<User>> getActiveUsers() {
        return ResponseEntity.ok(userService.findActiveUsers());
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }
}
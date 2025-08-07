package com.sante.reventionplatform.controller;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users1")
public class UserController1 {

    @Autowired
    private UserService1 userService1;

    @GetMapping
    public ResponseEntity<List<User1>> getAllUsers() {
        List<User1> users = userService1.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User1> getUserById(@PathVariable Long id) {
        Optional<User1> user = userService1.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User1> getUserByEmail(@PathVariable String email) {
        Optional<User1> user = userService1.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User1> createUser(@RequestBody User1 user) {
        try {
            User1 savedUser = userService1.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User1> updateUser(@PathVariable Long id, @RequestBody User1 userDetails) {
        try {
            User1 updatedUser = userService1.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService1.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
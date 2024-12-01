package com.example.petswithai.controller;

import com.example.petswithai.entity.User;
import com.example.petswithai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<User> resetPassword(@PathVariable Long id, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.resetPassword(id, newPassword));
    }

    @PutMapping("/{id}/toggle-unlocked")
    public ResponseEntity<User> toggleUnlocked(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUnlocked(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.petswithai.service;

import com.example.petswithai.entity.User;

public interface UserService {
    User createUser(User user);
    User resetPassword(Long id, String newPassword);
    User toggleUnlocked(Long id);
    void deleteUser(Long id);
}
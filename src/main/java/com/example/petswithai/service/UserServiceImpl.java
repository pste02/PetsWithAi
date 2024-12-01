package com.example.petswithai.service;

import com.example.petswithai.entity.User;
import com.example.petswithai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User resetPassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User toggleUnlocked(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUnlocked(!user.isUnlocked());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.User;
import com.mielibi.mielibi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUser() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("User not found by id [" + userId + "]"));
    }

    @Override
    public User createUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws Exception {
        User exUser = findUserById(user.getUserId());
        exUser.setPassword(user.getPassword());
        exUser.setUsername(user.getUsername());
        return userRepository.save(exUser);
    }

    @Override
    public void deleteUser(Long userID) throws Exception {
        userRepository.deleteById(userID);
    }

    @Override
    public User login(String username, String password) {
        User savedUser = findUserByUsername(username);
        String savedPassword = savedUser.getPassword();
        if(savedPassword.equals(password))
            return savedUser;
        else
            throw new IllegalArgumentException("Not found Username and Password");
    }

    @Override
    public User findUserByUsername(String username)  {
        return userRepository.findByUsername(username).orElseThrow( () -> new IllegalArgumentException("not found username = [" + username +"]"));
    }
}

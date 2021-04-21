package com.tweetapp.tweet.service;

import com.tweetapp.tweet.model.User;
import com.tweetapp.tweet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String registerUser(User user) throws Exception {
        Optional<User> userObject = userRepository.findByUserName(user.getUserName());

        if (userObject.isPresent()) {
            log.info("User already exist");
            throw new Exception("User already exist");
        } else {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        return "User created with following details: \nuser name: " + user.getUserName() + " Email ID: " + user.getEmailId();
    }

    public Optional<User> getUserByUserName(String username) {

        return userRepository.findByUserName(username);
    }

}

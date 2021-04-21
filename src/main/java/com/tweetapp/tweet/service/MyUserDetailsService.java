package com.tweetapp.tweet.service;

import com.tweetapp.tweet.model.MyUserDetails;
import com.tweetapp.tweet.model.User;
import com.tweetapp.tweet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new User("Omkar", "pass", new ArrayList<>());
        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Founnd: "+ userName));

        return user.map(MyUserDetails::new).get();
    }
}

package com.tweetapp.tweet.controller;

import com.tweetapp.tweet.model.AuthenticationRequest;
import com.tweetapp.tweet.model.AuthenticationResponse;
import com.tweetapp.tweet.model.User;
import com.tweetapp.tweet.service.MyUserDetailsService;
import com.tweetapp.tweet.service.SequenceGeneratorService;
import com.tweetapp.tweet.service.UserService;
import com.tweetapp.tweet.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/api/v1.0/tweets/users/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/api/v1.0/tweets/register")
    public String registerUser(@RequestBody User user) {
        try {
            user.setId(sequenceGeneratorService.getSequenceNumber(User.SEQUENCE_NAME));
            return userService.registerUser(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/api/v/1.0/tweets/user/search/{username}")
    public User getUserByUserName(@PathVariable String username) throws UsernameNotFoundException {

        Optional<User> user = userService.getUserByUserName(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+ username));
        return user.map(User::new).get();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/api/v1.0/tweets/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
        throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.
                loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

package com.tweetapp.tweet.service;

import com.tweetapp.tweet.model.User;
import com.tweetapp.tweet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "tweetapp.users";
    private static final String PASSWORD = "tweetapp@123";

    private static final String EMAIL_FROM = "tweetapp.users@gmail.com";
    private static final String EMAIL_TO = "";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Email Confirmation";
    private static final String EMAIL_TEXT = "";

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String registerUser(User user) throws Exception {
        Optional<User> userObject = userRepository.findByUserName(user.getUserName());

        if (userObject.isPresent()) {
            log.info("User already exist");
            throw new Exception("User already exist");
        } else {

            System.out.println(user);
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            Properties prop = System.getProperties();
            prop.put("mail.smtp.host", SMTP_SERVER);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "587"); // default port 25
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(prop, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(USERNAME));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailId()));
                msg.setSubject(EMAIL_SUBJECT);
                msg.setText("Hi, " + user.getUserName() + "\nClick on the link below to confirm your email.\n"
                        + "http://localhost:8082/api/v1.0/tweets/user/confirm/" + user.getId() + "/" + user.getEmailId());
                Transport.send(msg);
                log.debug("DONE");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }
        return "User created with following details: \nuser name: " + user.getUserName() + " Email ID: " + user.getEmailId();
    }

    public User confirmUser(int id, String emailId){
        //userRepository.confirmUser(true, id, emailId);
        User newUser =userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found."));
        newUser.setConfirmed(true);
        newUser.setEmailId(emailId);
        final User finalUser = userRepository.save(newUser);
        return finalUser;
    }

    public Optional<User> getUserByUserName(String username) {

        return userRepository.findByUserName(username);
    }

    public int getUserIdByUserName(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        User retrievedUser = new User();
        if (user.isPresent()){
            retrievedUser  =  user.map(User::new).get();
        }
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+ username));
        return retrievedUser.getId();
    }

    public User getUserById(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new Exception("User Not found"));
        return user.map(User::new).get();
    }
}

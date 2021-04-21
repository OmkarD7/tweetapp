package com.tweetapp.tweet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User")
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String userName;
    private String emailId;
    private String password;

    private boolean active;
    private String roles;

    public User(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.emailId = user.getEmailId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.contactNumber = user.getContactNumber();
    }
}

package com.tweetapp.tweet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private boolean success;
    private String jwt;
}

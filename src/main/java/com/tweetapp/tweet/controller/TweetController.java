package com.tweetapp.tweet.controller;

import com.tweetapp.tweet.exceptions.NotAuthorizedException;
import com.tweetapp.tweet.exceptions.ResourceNotFoundException;
import com.tweetapp.tweet.model.Tweet;
import com.tweetapp.tweet.service.SequenceGeneratorService;
import com.tweetapp.tweet.service.TweetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetController {
    @Autowired
    private TweetsService tweetsService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/api/v1.0/tweets/all")
    public List<Tweet> getAllTweets() {
        return tweetsService.getAllTweets();
    }

    @GetMapping("/api/v1.0/tweets/{username}")
    public List<Tweet> getTweetsByUserName(@PathVariable String username) {
        return tweetsService.getTweetsByUserName(username);
    }

    @PostMapping("/api/v1.0/tweets/{username}/add")
    public String postTweet(@RequestBody Tweet tweet, @PathVariable String username) {
        //generate sequence
        tweet.setId(sequenceGeneratorService.getSequenceNumber(Tweet.SEQUENCE_NAME));
        return tweetsService.postTweet(username, tweet);
    }

    @DeleteMapping("/api/v1.0/tweets/{username}/delete/{id}")
    public String deleteTweet(@PathVariable String username, @PathVariable int id) {
        return tweetsService.deleteTweet(id, username);
    }

    @PutMapping("/api/v1.0/tweets/{username}/update/{id}")
    public String updateTweet(@RequestBody Tweet tweet, @PathVariable String username,
                              @PathVariable int id) throws Exception{
        if (username.equals(tweet.getUserName())){
        return tweetsService.updateTweet(id, tweet);
        }else {
            throw new NotAuthorizedException("You are not allowed to update this tweet");
        }
    }

    @PutMapping("/api/v1.0/tweets/{username}/like/{id}")
    public String likeTweet(@PathVariable String username,
                            @PathVariable int id,
                            @RequestBody Tweet tweet) throws ResourceNotFoundException {
        return tweetsService.likeTweet(username, id, tweet);
    }

    /*@PostMapping("/api/v1.0/tweets/{username}/reply/{id}")
    public String replyToTweet(@PathVariable String username,  @PathVariable int id, @RequestBody Tweet tweet){
        return tweetsService.replyTweet(username, id, tweet);
    }*/

}

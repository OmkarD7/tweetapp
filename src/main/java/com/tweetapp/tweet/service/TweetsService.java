package com.tweetapp.tweet.service;

import com.tweetapp.tweet.exceptions.ResourceNotFoundException;
import com.tweetapp.tweet.model.Tweet;
import com.tweetapp.tweet.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TweetsService {
    @Autowired
    TweetRepository  tweetRepository;

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public String postTweet(String username, Tweet tweet) {
        tweetRepository.save(tweet);
        return "Tweet Posted  for user: "+ username;
    }

    public String updateTweet(int id, Tweet tweet) throws ResourceNotFoundException {
       Tweet retrievedTweet = tweetRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Tweet Not found"));
       retrievedTweet.setTweetBody(tweet.getTweetBody());
       final Tweet updatedTweet = tweetRepository.save(retrievedTweet);
       return "tweet updated "+ retrievedTweet.getTweetBody();
    }

    public String deleteTweet(int id, String username) {
        tweetRepository.deleteById(id);
        return "Tweet with ID: "+ id +" Deleted by user: "+username;
    }

    public List<Tweet> getTweetsByUserName(String username) {
        return tweetRepository.findByUserName(username);
    }

    public String likeTweet(String username, int id, Tweet tweet) throws ResourceNotFoundException {
        Tweet retrievedTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet Not found"));
        retrievedTweet.setNumberOfLikes(tweet.getNumberOfLikes());
        final Tweet updatedTweet = tweetRepository.save(retrievedTweet);
        return "Total Number of Likes for tweet id: "+ id+" = "+retrievedTweet.getNumberOfLikes();
    }
}
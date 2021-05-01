package com.tweetapp.tweet.repository;

import com.tweetapp.tweet.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, Integer> {
    List<Tweet> findByUserName(String username);

}

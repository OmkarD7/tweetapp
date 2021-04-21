package com.tweetapp.tweet.repository;

import com.tweetapp.tweet.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, Integer> {
    List<Tweet> findByUserName(String username);

    //@Modifying
    //@Transactional
    //@Query(value = "UPDATE tweet set tweet_body=?, number_of_likes=? WHERE id=?", nativeQuery = true)
    //void updateTweet(String tweetBody, int numberOfLikes, int id);
}

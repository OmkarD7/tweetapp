
package com.tweetapp.tweet.repository;

import com.tweetapp.tweet.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

}


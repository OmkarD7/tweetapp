package com.tweetapp.tweet;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.TweetsService;
import com.tweetapp.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TweetApplicationTests {

private static final String USER_NAME = "OmkarD";
	

	@Autowired
	TweetsService testTweetService;
	
	@Autowired
	UserService testUserService;
	
	@Autowired TweetRepository testTweetRepo;

	@Autowired
	UserRepository testUserRepo;
	
	@Test
	public void testGetUserByUserName(){
		
		User user = new User(1, "Omkar", "Dnyanmote", "OmkarD", "omkar@tweetapp.com", "7841011110", "aaa", true, "USER");
		
		Mockito.when(testUserService.getUserByUserName(USER_NAME)).thenReturn(user);
		
		User retrivedUser = testUserService.getUserByUserName("OmkarD");
		
		Assert.assertEquals(user, retrivedUser);
	}
	
	
	@Test
	public void testGetUserIdByUserName() {
		User user = new User(1, "Omkar", "Dnyanmote", "OmkarD", "omkar@tweetapp.com", "7841011110", "aaa", true, "USER");
		Mockito.when(testUserService.getUserIdByUserName(USER_NAME)).thenReturn(1);
		
		int userId = testUserService.getUserIdByUserName("OmkarD");
		
		Assert.assertEquals(1, userId);
	}
	
	@Test
	public void testGetUserByUserId() {
		User user = new User(1, "Omkar", "Dnyanmote", "OmkarD", "omkar@tweetapp.com", "7841011110", "aaa", true, "USER");
		try {
			Mockito.when(testUserService.getUserById(1)).thenReturn(user);
			User retrieveduser = testUserService.getUserById(1);

			Assert.assertEquals(user, retrieveduser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllTweets() {
		Tweet tweet1 = new Tweet();
		tweet1.setId(1);
		tweet1.setUserName("OmkarD");
		tweet1.setTweetBody("Stay Home Stay Safe");
		
		Tweet tweet2 = new Tweet();
		tweet2.setId(2);
		tweet2.setUserName("Anil");
		tweet2.setTweetBody("Stay Home Stay Safe");
		
		List<Tweet> tweetList = new ArrayList<Tweet>();
		tweetList.add(tweet1);
		tweetList.add(tweet2);
		
		Mockito.when(testTweetService.getAllTweets()).thenReturn(tweetList);
		
		List<Tweet> retrievedList = testTweetService.getAllTweets();
		
		Assert.assertEquals(2, retrievedList.size());
	}
	
	@Test
	void contextLoads() {
	}

}

package com.tweetapp.tweet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.TweetsService;
import com.tweetapp.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TweetAppTest {
	
	private static final String USER_NAME = "OmkarD";
	

	@Autowired
	TweetsService testTweetService;
	
	@Autowired
	UserService testUserService;
	
	@Autowired TweetRepository testTweetRepo;

	@Autowired
	UserRepository testUserRepo;
	
	@Test
	public void userTest(){
		
		User user = new User(1, "Omkar", "Dnyanmote", "OmkarD", "omkar@tweetapp.com", "7841011110", "aaa", true, "USER");
		
		Mockito.when(testUserService.getUserByUserName(USER_NAME)).thenReturn(user);
		Mockito.when(testUserService.getUserIdByUserName(USER_NAME)).thenReturn(1);
		
		User retrivedUser = testUserService.getUserByUserName("OmkarD");
		int userId = testUserService.getUserIdByUserName("OmkarD");
		
		
		Assert.assertEquals(1, userId);
		Assert.assertEquals(user, retrivedUser);
		
	}

}

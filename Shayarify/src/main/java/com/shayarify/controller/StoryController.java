package com.shayarify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.model.Story;
import com.shayarify.model.User;
import com.shayarify.service.StoryService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class StoryController {

	@Autowired
	private StoryService storyService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/story")
	public Story createStory(@RequestBody Story story,
			@RequestHeader ("Authorization")String jwt) {
		
		User reqUser = userService.findUserByJwt(jwt);
		Story createdStory = storyService.createStory(story, reqUser);
		return createdStory;
		
		
	}
	
	@GetMapping("/story/user/{userId}")
	public List<Story> findUserStory(
			@RequestHeader ("Authorization")String jwt,
			@PathVariable Integer userId) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		List<Story> stories = storyService.findStoryByUserId(userId);
		return stories;
		
		
	}
}

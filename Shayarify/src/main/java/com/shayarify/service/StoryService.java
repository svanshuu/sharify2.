package com.shayarify.service;

import java.util.List;

import com.shayarify.model.Story;
import com.shayarify.model.User;

public interface StoryService {

	public Story createStory(Story story, User user);
	
	public List<Story> findStoryByUserId(Integer userId) throws Exception;
}

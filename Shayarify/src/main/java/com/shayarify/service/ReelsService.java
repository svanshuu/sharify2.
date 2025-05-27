package com.shayarify.service;

import java.util.List;

import com.shayarify.model.Reels;
import com.shayarify.model.User;

public interface ReelsService  {

	public Reels createReel(Reels reel, User user);
	
	public List<Reels> findAllReels();
	
	public List<Reels> findUsersReel(Integer userId) throws Exception;
	
}

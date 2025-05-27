package com.shayarify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayarify.model.Reels;
import com.shayarify.model.User;
import com.shayarify.repository.ReelsRepository;

@Service
public class ReelsServiceImplementation  implements ReelsService{

	@Autowired
	private ReelsRepository reelsRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Reels createReel(Reels reel, User user) {
	    Reels createdReel = new Reels();
	    createdReel.setTitle(reel.getTitle());
	    createdReel.setUser(user);
	    createdReel.setVideo(reel.getVideo());
	    return reelsRepository.save(createdReel);
	}

	@Override
	public List<Reels> findAllReels() {
		// TODO Auto-generated method stub
		return reelsRepository.findAll();
	}

	@Override
	public List<Reels> findUsersReel(Integer userId) throws Exception {

		userService.findUserById(userId);
		
		return reelsRepository.findByUserId(userId);
	}

}

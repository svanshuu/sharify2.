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

import com.shayarify.model.Reels;
import com.shayarify.model.User;
import com.shayarify.service.ReelsService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class ReelsController {

	@Autowired
	private ReelsService reelsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/reels")
	public Reels createReels(@RequestBody Reels reel,
			@RequestHeader("Authorization")String jwt) {
		
		User reqUser = userService.findUserByJwt(jwt);
		Reels createdReels = reelsService.createReel(reel, reqUser);
		
		return createdReels;
	}
	
	@GetMapping("/reels")
	public List<Reels> findAllReels() {
	    return reelsService.findAllReels();
	}
	
	@GetMapping("/reels/user/{userId}")
	public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception {
		
		List<Reels> reels = reelsService.findUsersReel(userId);
		
		return reels;
	}
}

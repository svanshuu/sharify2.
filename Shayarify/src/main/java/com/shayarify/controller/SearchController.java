package com.shayarify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.model.User;
import com.shayarify.repository.UserRepository;
import com.shayarify.service.UserService;

@RestController
public class SearchController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user/search")
	public List<User>searchUser(@RequestParam("query")String query){
		List<User>users = userService.searchUser(query);
		return users;
	}
}

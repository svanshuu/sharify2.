package com.shayarify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.model.User;
import com.shayarify.repository.UserRepository;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/user")
	public List<User> getUser() {
		
		List<User> users = userRepository.findAll();
		
		return users;
	}
	@GetMapping("/user/{userId}")
	public User getUserById(@PathVariable("userId")Integer id) throws Exception{
		
		User user = userService.findUserById(id);
		return user;
		
		
	
	}
	
	
 	
	@PutMapping("/user")
	public User updateUser(@RequestHeader ("Authorization")String jwt,@RequestBody User user) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		User updatedUser = userService.updateUser(user, reqUser.getId());
		
		return updatedUser;
	}
	
	@DeleteMapping("/user/{userId}")
	public String deleteUser(@PathVariable Integer userId) throws Exception {
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new Exception ("user not exist with id "+userId);
		}
		
		userRepository.delete(user.get());
		return "user deleted successfully with id "+userId;
	}
	
//	@PutMapping("/user/follow/{userId2}")
//	public User followUserHandler(@RequestHeader ("Authorization")String jwt,@PathVariable Integer userId2) throws Exception {
//		
//		User reqUser = userService.findUserByJwt(jwt);
//		User user = userService.followUser(reqUser.getId(), userId2);
//		return user;
//				
//	}
	
	@PutMapping("/user/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) throws Exception {
	    User reqUser = userService.findUserByJwt(jwt);
	    return userService.followUser(reqUser.getId(), userId2);
	}

	@PutMapping("/user/unfollow/{userId2}")
	public User unfollowUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) throws Exception {
	    User reqUser = userService.findUserByJwt(jwt);
	    return userService.unfollowUser(reqUser.getId(), userId2);
	}

	@GetMapping("/user/profile")
	public User getUserFromToken(@RequestHeader ("Authorization")String jwt) {
		
	
	//	System.out.println("jwt-----------"+jwt);
		User user = userService.findUserByJwt(jwt);
		
		user.setPassword(null);
		return user;
	}
}

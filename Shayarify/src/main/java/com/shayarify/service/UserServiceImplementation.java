package com.shayarify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayarify.config.JwtProvider;
import com.shayarify.exceptions.UserException;
import com.shayarify.model.User;
import com.shayarify.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired 
	UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setGender(user.getGender());
		newUser.setId(user.getId());
		
		User savedUser = userRepository.save(newUser);
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
	Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("user not exist with userid 0"+userId);
	
	}

	@Override
	public User findUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email);
		return user;
	}

//	@Override
//	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
//		
//		User reqUser = findUserById(reqUserId);
//		
//		User user2 = findUserById(userId2);
//		
//		user2.getFollowers().add(reqUser.getId());
//		reqUser.getFollowings().add(user2.getId());
//		
//		userRepository.save(reqUser);
//		userRepository.save(user2);
//		return reqUser;// user1 follow user2
//	}
	@Override
	@Transactional
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
	    User reqUser = findUserById(reqUserId);
	    User user2 = findUserById(userId2);

	    // Check if already following
	    if (user2.getFollowers().contains(reqUser.getId())) {
	        throw new UserException("You are already following this user.");
	    }

	    // Add to followers and followings
	    user2.getFollowers().add(reqUser.getId());
	    reqUser.getFollowings().add(user2.getId());

	    userRepository.save(reqUser);
	    userRepository.save(user2);
	    return reqUser; // Return the requesting user after the operation
	}

	@Override
	@Transactional
	public User unfollowUser(Integer reqUserId, Integer userId2) throws UserException {
	    User reqUser = findUserById(reqUserId);
	    User user2 = findUserById(userId2);

	    // Check if not following
	    if (!user2.getFollowers().contains(reqUser.getId())) {
	        throw new UserException("You are not following this user.");
	    }

	    // Remove from followers and followings
	    user2.getFollowers().remove(reqUser.getId());
	    reqUser.getFollowings().remove(user2.getId());

	    userRepository.save(reqUser);
	    userRepository.save(user2);
	    return reqUser; // Return the requesting user after the operation
	}

	@Override
	public User updateUser(User user,Integer userId) throws UserException {
		
		Optional<User> user1 = userRepository.findById(userId);
		
		if(user1.isEmpty()) {
			throw new UserException ("user not exist with id "+userId);
		}
		User oldUser = user1.get();
		
		if(user.getFirstName()!=null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if(user.getLastName()!=null) {
			oldUser.setLastName(user.getLastName());
		}
		if(user.getEmail()!=null) {
			oldUser.setEmail(user.getEmail());
		}
		if(user.getGender()!=null) {
			oldUser.setGender(user.getGender());
		}
		if(user.getBio()!=null) {
			oldUser.setBio(user.getBio());
		}
		if(user.getAvatar()!=null) {
			oldUser.setAvatar(user.getAvatar());
		}
		if(user.getPassword()!=null) {
			oldUser.setPassword(user.getPassword());
		}
		User updateUser = userRepository.save(oldUser);
		return updateUser; 
	}

	@Override
	public List<User> searchUser(String query) {
		
		return userRepository.searchUser(query);
	}

	@Override
	public User findUserByJwt(String jwt) {
		
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}

}

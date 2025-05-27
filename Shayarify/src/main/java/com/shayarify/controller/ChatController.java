package com.shayarify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.model.Chat;
import com.shayarify.model.User;
import com.shayarify.request.CreateChatRequest;
import com.shayarify.service.ChatService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/chats")
	public Chat createChat(@RequestBody CreateChatRequest req,
			@RequestHeader ("Authorization")String jwt) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);

		User user2 = userService.findUserById(req.getUserId());
		
		Chat chat= chatService.createChat(reqUser, user2);
		
		return chat;
	}
	
	@GetMapping("/chats")
	public List<Chat> findUsersChat(@RequestHeader ("Authorization")String jwt) {
		
		User user = userService.findUserByJwt(jwt);
		
		List<Chat> chats = chatService.findUsersChat(user.getId());
		
		return chats;
	}
}

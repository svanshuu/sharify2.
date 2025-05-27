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

import com.shayarify.model.Message;
import com.shayarify.model.User;
import com.shayarify.service.MessageService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class CreateMessage {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/messages/chat/{chatId}")
	public Message createMessage(@RequestBody Message req,
			@RequestHeader ("Authorization")String jwt,
			@PathVariable Integer chatId) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Message message = messageService.createMessage(user, chatId,req );
		return message;
	}
	
	@GetMapping("/messages/chat/{chatId}")
	public List<Message> findChatMessage(
			@RequestHeader ("Authorization")String jwt,
			@PathVariable Integer chatId) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		List<Message> messages = messageService.findChatsMessages(chatId);
		return messages;
	}
}

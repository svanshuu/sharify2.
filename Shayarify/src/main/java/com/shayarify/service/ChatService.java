package com.shayarify.service;

import java.util.List;

import com.shayarify.model.Chat;
import com.shayarify.model.User;

public interface ChatService {

	public Chat createChat(User reqUser, User user2);
	
	public Chat findChatById(Integer chatId) throws Exception;
	
	public List<Chat> findUsersChat (Integer userId );
	
	
}

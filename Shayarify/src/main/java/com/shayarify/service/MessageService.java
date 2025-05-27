package com.shayarify.service;

import java.util.List;

import com.shayarify.model.Chat;
import com.shayarify.model.Message;
import com.shayarify.model.User;

public interface MessageService {

	public Message createMessage(User user, Integer chatId, Message req) throws Exception;
	
	public List<Message> findChatsMessages(Integer chatId) throws Exception;
}

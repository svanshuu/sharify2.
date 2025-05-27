package com.shayarify.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayarify.model.Chat;
import com.shayarify.model.User;
import com.shayarify.repository.ChatRepository;

@Service
public class ChatServiceImplementations implements ChatService {

	@Autowired
	ChatRepository chatRepository;
	
	
	public Chat createChat(User reqUser, User user2) {
	    if (reqUser.getId().equals(user2.getId())) {
	        throw new IllegalArgumentException("Cannot create chat with yourself.");
	    }
	    Chat isExist = chatRepository.findChatByUsersId(user2, reqUser);
	    if(isExist != null) {
	        return isExist;
	    }
	    Chat chat = new Chat();
	    chat.getUsers().add(user2);
	    chat.getUsers().add(reqUser);
	    chat.setTimeStamp(LocalDateTime.now());
	    return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws Exception {

		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isEmpty()) {
			throw new Exception("Chat is not found with id "+ chatId);
			
		}
		return opt.get();
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {

		
		return chatRepository.findByUsersId(userId);
	}

}

package com.shayarify.service;

import com.shayarify.model.Comment;

public interface CommentService {

	public Comment createComment (
			Comment comment,
			Integer postId,
			Integer userId) throws Exception;
	
	public Comment findCommentById(Integer commentId) throws Exception;
	public Comment likeComment(Integer CommentId, Integer userId) throws Exception;
}

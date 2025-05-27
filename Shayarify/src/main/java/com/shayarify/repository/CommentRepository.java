package com.shayarify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayarify.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	
}

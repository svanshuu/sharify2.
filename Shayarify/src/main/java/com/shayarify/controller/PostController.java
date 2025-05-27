package com.shayarify.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.dto.PostDTO;
import com.shayarify.exceptions.UserException;
import com.shayarify.model.Post;
import com.shayarify.model.User;
import com.shayarify.response.ApiResponse;
import com.shayarify.service.PostService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired 
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/posts")
	public ResponseEntity<Post>createPost(@RequestHeader ("Authorization")String jwt,
			@RequestBody Post post) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		Post createdPost = postService.createNewPost(post, reqUser.getId());
		
		return new ResponseEntity<>(createdPost,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,
			@RequestHeader ("Authorization")String jwt) throws Exception{
		
		User reqUser = userService.findUserByJwt(jwt);
		String message = postService.deletePost(postId, reqUser.getId());
		ApiResponse res = new ApiResponse(message,true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<Post>findPostByIdHandler(@PathVariable Integer postId) throws Exception{
		Post post = postService.findPostById(postId);
		
		return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDTO>> findUsersPost(@PathVariable Integer userId) throws UserException {
	    List<PostDTO> posts = postService.findPostByUserId(userId);
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
//	@GetMapping("/posts")
//	public ResponseEntity<List<Post>>findAllPost(){
//		List<Post> posts = postService.findAllPost();
//		
//		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
//	}
	@GetMapping("/posts")
	public ResponseEntity<List<PostDTO>> findAllPost() {
	    List<PostDTO> postDTOs = postService.findAllPost();  // Should be List<PostDTO>, not List<Post>
	    return new ResponseEntity<>(postDTOs, HttpStatus.OK);
	}

	@GetMapping("/posts/saved")
	public ResponseEntity<List<PostDTO>> getSavedPosts(@RequestHeader("Authorization") String jwt) throws Exception {
	    User reqUser = userService.findUserByJwt(jwt);
	    List<PostDTO> posts = postService.getSavedPosts(reqUser.getId());
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	@PutMapping("/posts/save/{postId}")
	public ResponseEntity<?> savedPostHandler(@PathVariable Integer postId,
	        @RequestHeader("Authorization") String jwt) throws Exception {

	    User reqUser = userService.findUserByJwt(jwt);
	    Post post = postService.savedPost(postId, reqUser.getId());

	    // Check if post is now saved for the user
	    boolean isSaved = reqUser.getSavedPost().contains(post);

	    // Return the save status
	    return ResponseEntity.accepted().body(Map.of("isSaved", isSaved));
	}
	
	@PutMapping("/posts/like/{postId}")
	public ResponseEntity<Post>likePostHandler(@PathVariable Integer postId,
			@RequestHeader ("Authorization")String jwt) throws Exception{
		
		User reqUser = userService.findUserByJwt(jwt);

		Post post = postService.likePost(postId,reqUser.getId());
		
		return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
	}
	
}

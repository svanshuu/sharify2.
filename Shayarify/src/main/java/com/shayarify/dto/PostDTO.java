package com.shayarify.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private Integer id;
    private String caption;
    private String image;
    private String video;
    private UserDTO user;
    private List<UserDTO> liked;
    private LocalDateTime createdAt;
    private List<CommentDTO> comments;
    private List<String> hashtags;
    private boolean isSaved; // + getter/setter
    
	public PostDTO(Integer id, String caption, String image, String video, UserDTO user, List<UserDTO> liked,
			LocalDateTime createdAt, List<CommentDTO> comments, List<String> hashtags, boolean isSaved) {
		super();
		this.id = id;
		this.caption = caption;
		this.image = image;
		this.video = video;
		this.user = user;
		this.liked = liked;
		this.createdAt = createdAt;
		this.comments = comments;
		this.hashtags = hashtags;
		this.isSaved = isSaved;
	}
	public boolean isSaved() {
		return isSaved;
	}
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public List<UserDTO> getLiked() {
		return liked;
	}
	public void setLiked(List<UserDTO> liked) {
		this.liked = liked;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	public List<String> getHashtags() {
		return hashtags;
	}
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

   
}

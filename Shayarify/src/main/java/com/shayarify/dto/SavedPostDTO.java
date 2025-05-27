package com.shayarify.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SavedPostDTO {
    private Integer id;
    private String caption;
    private String image;
    private String video;
    private UserDTO user;
    private LocalDateTime createdAt;
    private List<String> hashtags;

    public SavedPostDTO() {}

    public SavedPostDTO(
        Integer id,
        String caption,
        String image,
        String video,
        UserDTO user,
        LocalDateTime createdAt,
        List<String> hashtags
    ) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.video = video;
        this.user = user;
        this.createdAt = createdAt;
        this.hashtags = hashtags;
    }

    // Getters and setters

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
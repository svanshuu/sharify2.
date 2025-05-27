package com.shayarify.dto;

public class CommentDTO {

    private Integer id;
    private String content;
    private UserDTO user;

    // Constructor
    public CommentDTO(Integer id, String content, UserDTO user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

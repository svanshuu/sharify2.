package com.shayarify.dto;

import java.util.List;

public class UserDTO {
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean termsAccepted;
    private List<Integer> followers;
    private List<Integer> followings;
    private String gender;
    private String avatar;

    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	// Constructor
    public UserDTO(Integer id, String firstName, String lastName, String email, boolean termsAccepted,
                   List<Integer> followers, List<Integer> followings, String gender,String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.termsAccepted = termsAccepted;
        this.followers = followers;
        this.followings = followings;
        this.gender = gender;
        this.avatar =avatar;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package com.server.backend.dto;

public class AuthResponse {

    private int userId;
    private String userEmail;

    public AuthResponse() {
    }

    public AuthResponse(int userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

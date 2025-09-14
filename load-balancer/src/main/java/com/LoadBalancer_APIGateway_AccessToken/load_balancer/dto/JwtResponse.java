package com.LoadBalancer_APIGateway_AccessToken.load_balancer.dto;

/**
 * JWT response DTO containing the generated token
 * 
 * @author Sebastián Cañón - JWT Authentication Implementation
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private long expiresIn;

    // Default constructor
    public JwtResponse() {}

    // Constructor with token
    public JwtResponse(String token, String username, long expiresIn) {
        this.token = token;
        this.username = username;
        this.expiresIn = expiresIn;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
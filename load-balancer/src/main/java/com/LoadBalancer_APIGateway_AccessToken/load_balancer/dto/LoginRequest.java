package com.LoadBalancer_APIGateway_AccessToken.load_balancer.dto;

/**
 * Login request DTO for user authentication
 * 
 * @author Sebastián Cañón - JWT Authentication Implementation
 */
public class LoginRequest {
    private String username;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Constructor with parameters
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
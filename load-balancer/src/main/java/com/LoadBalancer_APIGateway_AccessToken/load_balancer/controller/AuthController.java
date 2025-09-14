package com.LoadBalancer_APIGateway_AccessToken.load_balancer.controller;

import com.LoadBalancer_APIGateway_AccessToken.load_balancer.dto.JwtResponse;
import com.LoadBalancer_APIGateway_AccessToken.load_balancer.dto.LoginRequest;
import com.LoadBalancer_APIGateway_AccessToken.load_balancer.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller for JWT token generation
 * Implements Access Token pattern login endpoint
 * 
 * @author Sebastián Cañón - JWT Authentication Implementation
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * Login endpoint to authenticate user and generate JWT token
     * 
     * @param loginRequest Contains username and password
     * @return JWT token response or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Get user details from authentication
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            // Create response with token and user info
            JwtResponse jwtResponse = new JwtResponse(
                token,
                userDetails.getUsername(),
                jwtExpiration / 1000 // Convert to seconds
            );

            return ResponseEntity.ok(jwtResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest()
                .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint for authentication service
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("JWT Authentication Service is running");
    }

    /**
     * Get current user info (requires valid JWT token)
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok()
                .body("Current user: " + userDetails.getUsername() + 
                     " with authorities: " + userDetails.getAuthorities());
        }
        return ResponseEntity.badRequest().body("No authenticated user");
    }
}
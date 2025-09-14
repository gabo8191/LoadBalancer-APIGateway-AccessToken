package com.LoadBalancer_APIGateway_AccessToken.load_balancer.service;

import com.LoadBalancer_APIGateway_AccessToken.load_balancer.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User Details Service Implementation for JWT Authentication
 * Uses in-memory users for academic project demonstration
 * 
 * @author Sebastián Cañón - JWT Authentication Implementation
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // In-memory user storage for academic project
    private final Map<String, User> users = new HashMap<>();
    private boolean initialized = false;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Initialize default users with freshly encoded passwords
     * Called lazily to avoid circular dependency
     */
    private void initializeUsers() {
        if (!initialized) {
            // Create users with fresh BCrypt encoding
            User admin = new User("admin", passwordEncoder.encode("password"), "ADMIN");
            users.put("admin", admin);

            User user = new User("user", passwordEncoder.encode("user123"), "USER");
            users.put("user", user);

            User sebastian = new User("sebastian", passwordEncoder.encode("jwt2024"), "USER");
            users.put("sebastian", sebastian);

            System.out.println("=== USER INITIALIZATION DEBUG ===");
            System.out.println("Admin password hash: " + admin.getPassword());
            System.out.println("User password hash: " + user.getPassword());
            System.out.println("Sebastian password hash: " + sebastian.getPassword());
            System.out.println("=== END DEBUG ===");

            initialized = true;
        }
    }

    /**
     * Load user by username for Spring Security authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Initialize users if not already done
        initializeUsers();

        User user = users.get(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return user;
    }

    /**
     * Get user by username (for authentication controller)
     */
    public User findUserByUsername(String username) {
        initializeUsers();
        return users.get(username.toLowerCase());
    }

    /**
     * Add a new user (for future extensibility)
     */
    public void addUser(String username, String encodedPassword, String role) {
        User newUser = new User(username, encodedPassword, role);
        users.put(username.toLowerCase(), newUser);
    }
}
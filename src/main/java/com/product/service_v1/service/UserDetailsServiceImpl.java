package com.product.service_v1.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // For demo: username = user, password = password
        if ("user".equals(username)) {
            return new User("user", "password", Collections.singleton(() -> "ROLE_USER"));
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}

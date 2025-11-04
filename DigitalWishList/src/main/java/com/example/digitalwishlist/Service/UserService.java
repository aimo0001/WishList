package com.example.digitalwishlist.Service;

import com.example.digitalwishlist.Model.User;
import com.example.digitalwishlist.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public User register(String email, String rawPassword) {
        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already in use");
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(rawPassword));
        return users.save(u);
    }

    public User requireByEmail(String email) {
        return users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

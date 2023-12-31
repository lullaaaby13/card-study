package com.lullaby.cardstudy.appliation.authenticate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean match(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

}

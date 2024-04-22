package com.example.oauth2systemauthserver.service;

import com.example.oauth2systemauthserver.entity.AuthProvider;
import com.example.oauth2systemauthserver.entity.User;
import com.example.oauth2systemauthserver.helper.UserRegistrationDto;
import com.example.oauth2systemauthserver.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(UserRegistrationDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException("Email already used");
        }
        User user1 = new User();
        user1.setEmail(userDto.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user1.setProvider(AuthProvider.local);

        System.out.println(user1.getEmail());
        userRepository.save(user1);

        return "Successfully created User";
    }
}

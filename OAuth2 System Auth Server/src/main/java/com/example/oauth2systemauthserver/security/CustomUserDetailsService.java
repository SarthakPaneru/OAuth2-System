package com.example.oauth2systemauthserver.security;

import com.example.oauth2systemauthserver.entity.User;
import com.example.oauth2systemauthserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
//    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) {
        User user = (User) userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email: " + email + "not found")
        );

        return UserPrincipal.create(user);
    }
}
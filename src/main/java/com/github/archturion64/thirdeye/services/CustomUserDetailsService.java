package com.github.archturion64.thirdeye.services;

import com.github.archturion64.thirdeye.domains.User;
import com.github.archturion64.thirdeye.repositories.UserRepository;
import com.github.archturion64.thirdeye.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("no user %s found", email)));
        return new CustomUserDetails(user);
    }
}

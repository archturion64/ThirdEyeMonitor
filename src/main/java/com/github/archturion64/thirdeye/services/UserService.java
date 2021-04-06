package com.github.archturion64.thirdeye.services;

import com.github.archturion64.thirdeye.domains.User;
import com.github.archturion64.thirdeye.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String email) throws IllegalTransactionStateException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("unknown email %s", email)));
    }

    public User save(User user) throws IllegalTransactionStateException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalTransactionStateException(String.format("user with email %s already present", user.getEmail()));
        }
        userRepository.save(user);
        return userRepository.findById(user.getId())
                .orElseThrow(() ->
                        new IllegalTransactionStateException(String.format("unable to save user %s", user.getEmail())));
    }
}

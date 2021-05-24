package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.entities.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    public Mono<User> insert(final User user){
        return userRepository.insert(user);
    }

    public Mono<User> save(final User user){
        return userRepository.save(user);
    }

    public Mono<Boolean> existsByEmail(final String email){
        return userRepository.existsByEmail(email);
    }

    public Mono<Boolean> existsByEmailAndPassword(final String email, final String password){
        return userRepository.existsByEmailAndPassword(email, password);
    }

    public Mono<User> findByEmail(final String email){
        return userRepository.findByEmail(email);
    }

    public Mono<Void> deleteByEmail(final String email){
        return userRepository.deleteByEmail(email);
    }

}

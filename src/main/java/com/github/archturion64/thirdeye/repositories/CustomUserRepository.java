package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.entities.User;
import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;

public interface CustomUserRepository {

    Mono<User> findByEmail(String email);

    Mono<DeleteResult> deleteByEmail(String email);

    Mono<Boolean> existsByEmailAndPassword(String email, String password);
}

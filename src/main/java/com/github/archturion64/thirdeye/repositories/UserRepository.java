package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.entities.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByEmailAndPassword(String email, String password);

    Mono<User> findByEmail(String email);

    Mono<Void> deleteByEmail(String email);

}

package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.entities.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<User> findByEmail(String email) {
        return null;
    }

    @Override
    public Mono<DeleteResult> deleteByEmail(String email) {
        return mongoTemplate.remove(new Query(Criteria.where("email").is(email)), User.class);
    }

    @Override
    public Mono<Boolean> existsByEmailAndPassword(String email, String password) {
        return mongoTemplate.exists(new Query(Criteria
                .where("email").is(email)
                .and("password").is(password)), User.class);
    }

}

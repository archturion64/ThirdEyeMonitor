package com.github.archturion64.thirdeye.handlers;

import com.github.archturion64.thirdeye.dtos.UserDto;
import com.github.archturion64.thirdeye.repositories.UserDao;
import com.github.archturion64.thirdeye.utilities.DtoTranslation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class UserHandler {

    //private final UserRepository userRepository;

    private final UserDao userDao;

    public Mono<ServerResponse> saveUser(ServerRequest request) {

        Mono<UserDto> result = request
                .bodyToMono(UserDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmail(u.getEmail())
                        .map(exist -> !exist))
                .switchIfEmpty(Mono.error(new Exception("There is an account with that email address")))
                .doOnNext(e -> e.setDevices(new HashMap<>()))
                .flatMap(userDao::insert)
                .map(DtoTranslation::entityToDto);

        return ServerResponse.ok().body(result, UserDto.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {

        final String password = request.queryParam("password").orElse(null);
        if (password == null){
            return ServerResponse.badRequest().body("missing password request parameter", String.class);
        }

        Mono<UserDto> result = request
                .bodyToMono(UserDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmailAndPassword(u.getEmail(), password))
                .switchIfEmpty(Mono.error(new Exception("invalid credentials")))
                .flatMap(u -> userDao.findByEmail(u.getEmail())
                    .doOnNext(e -> {
                        e.setFirstName(u.getFirstName());
                        e.setLastName(u.getLastName());
                        e.setPassword(u.getPassword());
                    }))
                .flatMap(userDao::save)
                .map(DtoTranslation::entityToDto);

        return ServerResponse.ok().body(result, UserDto.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {

        final String password = request.queryParam("password")
                .orElse(null);
        if (password == null){
            return ServerResponse.badRequest().body("missing password request parameter", String.class);
        }

        Mono<Void> mono = request
                .bodyToMono(UserDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmailAndPassword(u.getEmail(), password))
                .switchIfEmpty(Mono.error(new Exception("invalid credentials")))
                .flatMap(u -> userDao.deleteByEmail(u.getEmail()));
        return ServerResponse.ok().body(mono, Void.class);
    }
}

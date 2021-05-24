package com.github.archturion64.thirdeye.handlers;

import com.github.archturion64.thirdeye.dtos.DeviceDto;
import com.github.archturion64.thirdeye.dtos.UserDto;
import com.github.archturion64.thirdeye.entities.Device;
import com.github.archturion64.thirdeye.entities.DeviceInfo;
import com.github.archturion64.thirdeye.repositories.UserDao;
import com.github.archturion64.thirdeye.utilities.DtoTranslation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceHandler {

    //private final UserRepository userRepository;

    private final UserDao userDao;

    public Mono<ServerResponse> getDeviceList(ServerRequest request) {

        final String account = request.queryParam("account").orElse(null);
        final String password = request.queryParam("password").orElse(null);
        if (account == null || password == null){
            return ServerResponse.badRequest().body("missing credentials as request parameter", String.class);
        }

        final Mono<List<DeviceDto>> result = request
                .bodyToMono(DeviceDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmailAndPassword(account, password))
                .switchIfEmpty(Mono.error(new Exception("invalid credentials")))
                .flatMap(u -> userDao.findByEmail(account))
                .map(g -> g.getDevices())
                .map(DtoTranslation::mapToList);
        return ServerResponse.ok().body(result, List.class);
    }


    public Mono<ServerResponse> saveDevice(ServerRequest request) {
        final String account = request.queryParam("account").orElse(null);
        final String password = request.queryParam("password").orElse(null);
        if (account == null || password == null){
            return ServerResponse.badRequest().body("missing credentials as request parameter", String.class);
        }

        Mono<UserDto> result = request
                .bodyToMono(DeviceDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmailAndPassword(account, password))
                .switchIfEmpty(Mono.error(new Exception("invalid credentials")))
                .flatMap(d -> userDao.findByEmail(account)
                        .doOnNext(e -> {
                            e.addDevice(d.getMacAddress(), d.getInfo());
                        }))
                .flatMap(userDao::save)
                .map(DtoTranslation::entityToDto);
        return ServerResponse.ok().body(result, UserDto.class);
    }

    public Mono<ServerResponse> deleteDevice(ServerRequest request) {
        final String account = request.queryParam("account").orElse(null);
        final String password = request.queryParam("password").orElse(null);
        if (account == null || password == null){
            return ServerResponse.badRequest().body("missing credentials as request parameter", String.class);
        }

        Mono<UserDto> result = request
                .bodyToMono(DeviceDto.class)
                .map(DtoTranslation::dtoToEntity)
                .filterWhen(u -> userDao.existsByEmailAndPassword(account, password))
                .switchIfEmpty(Mono.error(new Exception("invalid credentials")))
                .flatMap(d -> userDao.findByEmail(account)
                        .doOnNext(e -> {
                            e.removeDevice(d.getMacAddress());
                        }))
                .flatMap(userDao::save)
                .map(DtoTranslation::entityToDto);
        return ServerResponse.ok().body(result, UserDto.class);
    }
}

package com.github.archturion64.thirdeye.routers;

import com.github.archturion64.thirdeye.handlers.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Autowired
    private UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(){
        return RouterFunctions.route()
                .POST("/users", userHandler::saveUser)
                .PUT("/users", userHandler::updateUser)
                .DELETE("/users", userHandler::deleteUser)
                .build();
    }
}

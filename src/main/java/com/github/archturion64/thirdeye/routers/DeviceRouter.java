package com.github.archturion64.thirdeye.routers;

import com.github.archturion64.thirdeye.handlers.DeviceHandler;
import com.github.archturion64.thirdeye.handlers.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DeviceRouter {

    @Autowired
    private DeviceHandler deviceHandler;

    @Bean
    public RouterFunction<ServerResponse> deviceRouterFunction(){
        return RouterFunctions.route()
                .GET("/devices", deviceHandler::getDeviceList)
                .POST("/devices", deviceHandler::saveDevice)
                .PUT("/devices", deviceHandler::saveDevice)
                .DELETE("/devices", deviceHandler::deleteDevice)
                .build();
    }
}

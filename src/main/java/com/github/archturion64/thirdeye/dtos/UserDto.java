package com.github.archturion64.thirdeye.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UserDto implements Serializable {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<DeviceDto> devices;
}

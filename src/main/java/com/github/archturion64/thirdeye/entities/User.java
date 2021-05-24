package com.github.archturion64.thirdeye.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Map<String, DeviceInfo> devices = new HashMap<>();

    public void addDevice(final String deviceMac, final DeviceInfo device){
        devices.put(deviceMac, device);
    }

    public void removeDevice(final String deviceMac){
        devices.remove(deviceMac);
    }
}

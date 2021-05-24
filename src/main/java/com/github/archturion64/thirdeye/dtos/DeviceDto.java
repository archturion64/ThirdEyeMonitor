package com.github.archturion64.thirdeye.dtos;

import com.github.archturion64.thirdeye.entities.Component;
import com.github.archturion64.thirdeye.entities.Vulnerability;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class DeviceDto implements Serializable {
    String macAddress;
    String name;
    String ipAddress;
    String os;
    private List<Component> components;
    private Set<Vulnerability> vulnerabilities;
}

package com.github.archturion64.thirdeye.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class DeviceInfo {
    String name;
    String ipAddress;
    String os;
    private List<Component> components;
    private Set<Vulnerability> vulnerabilities;
}

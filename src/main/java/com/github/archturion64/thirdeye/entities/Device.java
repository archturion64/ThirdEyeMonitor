package com.github.archturion64.thirdeye.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Device {
    String macAddress;
    DeviceInfo info;
}

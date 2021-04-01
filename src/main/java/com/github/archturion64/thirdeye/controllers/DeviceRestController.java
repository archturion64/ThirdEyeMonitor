package com.github.archturion64.thirdeye.controllers;

import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.domains.Vulnerability;
import com.github.archturion64.thirdeye.services.DeviceService;
import com.github.archturion64.thirdeye.services.VulnerabilityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@AllArgsConstructor
public class DeviceRestController {
    private final DeviceService deviceService;


    @GetMapping("/get/all")
    public List<Device> getAllDevices(){
        return deviceService.listAll();
    }

    @PostMapping("/add")
    public String addDevice(@RequestBody Device device) {
        deviceService.saveDeviceAndVulnerabilities(device);
        return "device added";
    }

    @PutMapping("/edit")
    public String editDevice(@RequestBody Device device) {
        deviceService.edit(device);
        return "device modified";
    }

    @PutMapping("/delete/{id}")
    public String deleteDevice(@PathVariable(name="id") long id) {
        deviceService.delete(id);
        return "device deleted";
    }
}

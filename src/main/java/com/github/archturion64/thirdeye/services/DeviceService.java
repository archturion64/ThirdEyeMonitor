package com.github.archturion64.thirdeye.services;

import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.domains.User;
import com.github.archturion64.thirdeye.domains.Vulnerability;
import com.github.archturion64.thirdeye.repositories.DeviceRepository;
import com.github.archturion64.thirdeye.repositories.VulnerabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final VulnerabilityRepository vulnerabilityRepository;
    private final UserService userService;

    public List<Device> listAll(Principal principal) {
        return getLoggedUser(principal).getDevices();
    }

    public void add(Principal principal, Device device) {
        final User user = getLoggedUser(principal);
        device.setUser(user);
        deviceRepository.save(device);
    }

    public void edit(Principal principal, Device device) throws IllegalAccessException {
        final Device storedDevice = this.getById(principal, device.getId());
        device.setVulnerabilities(storedDevice.getVulnerabilities());
        device.setUser(storedDevice.getUser());

        deviceRepository.save(device);
    }

    public Device get(Principal principal, Long deviceId) throws IllegalArgumentException, IllegalAccessException {
        return this.getById(principal, deviceId);
    }

    public void delete(Principal principal, Long deviceId) throws IllegalAccessException {
        final Device storedDevice = getById(principal, deviceId);
        deviceRepository.delete(storedDevice);
    }

    public void addCveToDevice(Principal principal, Long deviceId, String cveText) throws IllegalAccessException {
        final Vulnerability vulnerability =
                vulnerabilityRepository.findByVulnerabilityCve(cveText)
                        .orElseThrow(() ->
                                new IllegalArgumentException(String.format("Unknown id %s", cveText)));
        final Device storedDevice = this.getById(principal, deviceId);
        storedDevice.addVulnerability(vulnerability);

        deviceRepository.save(storedDevice);
    }

    public void removeCveFromDevice(Principal principal, Long deviceId, Long vulnerabilityId) throws IllegalAccessException {
        final Vulnerability vulnerability =
                vulnerabilityRepository.findById(vulnerabilityId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(String.format("Unknown id %s", vulnerabilityId)));
        final Device storedDevice = this.getById(principal, deviceId);
        storedDevice.removeVulnerability(vulnerability);

        deviceRepository.save(storedDevice);
    }

    private User getLoggedUser(Principal principal){
        final String email = principal.getName();

        return userService.findUserByEmail(email);
    }

    private Device getById(Principal principal, Long deviceId) throws IllegalAccessException {
        final Device storedDevice = deviceRepository.findById(deviceId)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Unknown id %d", deviceId)));
        if(getLoggedUser(principal) != storedDevice.getUser()){
            throw new IllegalAccessException("not allowed to edit device");
        }

        return storedDevice;
    }
}

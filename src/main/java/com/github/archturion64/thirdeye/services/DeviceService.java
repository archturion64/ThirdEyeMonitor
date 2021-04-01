package com.github.archturion64.thirdeye.services;

import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.domains.Vulnerability;
import com.github.archturion64.thirdeye.repositories.DeviceRepository;
import com.github.archturion64.thirdeye.repositories.VulnerabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final VulnerabilityRepository vulnerabilityRepository;

    public List<Device> listAll(){
        return deviceRepository.findAll();
    }

    public void saveDeviceAndVulnerabilities(Device device){
        deviceRepository.save(device);
    }

    public void saveDevice(Device device){
        final Device previous = this.get(device.getId());
        device.setVulnerabilities(previous.getVulnerabilities());
        deviceRepository.save(device);
    }

    public Device get(Long id) throws IllegalArgumentException {
        return deviceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Unknown id %d", id)));
    }

    public void delete(Long id){
        deviceRepository.deleteById(id);
    }

    public void edit(Device device) {
        deviceRepository.save(device);
    }

    public void addCveToDevice(Long deviceId, String cveText){
        final Vulnerability vulnerability =
                vulnerabilityRepository.findByVulnerabilityCve(cveText)
                        .orElseThrow(() ->
                                new IllegalArgumentException(String.format("Unknown id %s", cveText)));
        final Device device = this.get(deviceId);

        device.addVulnerability(vulnerability);
        deviceRepository.save(device);
    }

    public void removeCveFromDevice(Long deviceId, Long vulnerabilityId){
        final Vulnerability vulnerability =
                vulnerabilityRepository.findById(vulnerabilityId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(String.format("Unknown id %s", vulnerabilityId)));
        final Device device = this.get(deviceId);

        device.removeVulnerability(vulnerability);
        deviceRepository.save(device);
    }
}

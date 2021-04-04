package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.domains.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByUserId(Long userId);

}

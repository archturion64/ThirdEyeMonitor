package com.github.archturion64.thirdeye.repositories;

import com.github.archturion64.thirdeye.domains.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}

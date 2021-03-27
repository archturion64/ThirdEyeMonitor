package com.github.archturion64.thirdeye;

import com.github.archturion64.thirdeye.domains.Severity;
import com.github.archturion64.thirdeye.domains.Vulnerability;
import com.github.archturion64.thirdeye.repositories.VulnerabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThirdEyeMonitorApplication implements CommandLineRunner {

	@Autowired
	private VulnerabilityRepository vulnerabilityRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThirdEyeMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Vulnerability v1 = new Vulnerability(
				"CVE-2016-5195",
				"DirtyCow",
				"Race condition in mm/gup.c in the Linux kernel 2.x through 4.x before 4.8.3 allows local users to gain privileges by leveraging incorrect handling of a copy-on-write (COW) feature to write to a read-only memory mapping, as exploited in the wild in October 2016, aka \"Dirty COW.\" ",
				Severity.CRITICAL
		);
		Vulnerability v2 = new Vulnerability(
				"CVE-2017-0785",
				"BlueBorne",
				"A information disclosure vulnerability in the Android system (bluetooth). Product: Android. Versions: 4.4.4, 5.0.2, 5.1.1, 6.0, 6.0.1, 7.0, 7.1.1, 7.1.2, 8.0. Android ID: A-63146698. ",
				Severity.CRITICAL
		);

		vulnerabilityRepository.save(v1);
		vulnerabilityRepository.save(v2);
	}
}

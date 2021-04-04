package com.github.archturion64.thirdeye;

import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.domains.Severity;
import com.github.archturion64.thirdeye.domains.User;
import com.github.archturion64.thirdeye.domains.Vulnerability;
import com.github.archturion64.thirdeye.repositories.DeviceRepository;
import com.github.archturion64.thirdeye.repositories.UserRepository;
import com.github.archturion64.thirdeye.repositories.VulnerabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
public class ThirdEyeMonitorApplication implements CommandLineRunner {

	private final VulnerabilityRepository vulnerabilityRepository;
	private final DeviceRepository deviceRepository;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThirdEyeMonitorApplication.class, args);
	}

	@Override
	public void run(String... args){
		Vulnerability v1 = new Vulnerability(
				"CVE-2016-5195",
				"DirtyCow",
				"Race condition in mm/gup.c in the Linux kernel 2.x through 4.x before 4.8.3 allows local users to gain privileges by leveraging incorrect handling of a copy-on-write (COW) feature to write to a read-only memory mapping, as exploited in the wild in October 2016, aka \"Dirty COW.\" ",
				Severity.HIGH
		);
		Vulnerability v2 = new Vulnerability(
				"CVE-2017-0785",
				"BlueBorne",
				"A information disclosure vulnerability in the Android system (bluetooth). Product: Android. Versions: 4.4.4, 5.0.2, 5.1.1, 6.0, 6.0.1, 7.0, 7.1.1, 7.1.2, 8.0. Android ID: A-63146698. ",
				Severity.CRITICAL
		);
		Vulnerability v3 = new Vulnerability(
				"CVE-2019-0708",
				"BlueKeep",
				"A remote code execution vulnerability exists in Remote Desktop Services formerly known as Terminal Services when an unauthenticated attacker connects to the target system using RDP and sends specially crafted requests, aka 'Remote Desktop Services Remote Code Execution Vulnerability'. ",
				Severity.CRITICAL
		);
		Vulnerability v4 = new Vulnerability(
				"CVE-2020-0796",
				"SMBleed",
				"A remote code execution vulnerability exists in the way that the Microsoft Server Message Block 3.1.1 (SMBv3) protocol handles certain requests, aka 'Windows SMBv3 Client/Server Remote Code Execution Vulnerability'. ",
				Severity.CRITICAL
		);
		Vulnerability v5 = new Vulnerability(
				"CVE-2017-0144",
				"EternalBlue",
				"The SMBv1 server in Microsoft Windows Vista SP2; Windows Server 2008 SP2 and R2 SP1; Windows 7 SP1; Windows 8.1; Windows Server 2012 Gold and R2; Windows RT 8.1; and Windows 10 Gold, 1511, and 1607; and Windows Server 2016 allows remote attackers to execute arbitrary code via crafted packets, aka \"Windows SMB Remote Code Execution Vulnerability.\" This vulnerability is different from those described in CVE-2017-0143, CVE-2017-0145, CVE-2017-0146, and CVE-2017-0148. ",
				Severity.CRITICAL
		);

		User user = new User(); // forward declaration

		Device d1 = new Device(
				"Tony's Mac",
				"192.168.1.2",
				"MacOS",
				user
		);
		d1.addVulnerability(v1);
		d1.addVulnerability(v2);

		Device d2 = new Device(
				"Mike's Tablet",
				"192.168.1.3",
				"iOS",
				user
		);
		d2.addVulnerability(v1);

		Device d3 = new Device(
				"Sheela's Desktop",
				"192.168.1.4",
				"Arch Linux",
				user
		);

		Device d4 = new Device(
				"Matt's Desktop",
				"192.168.1.5",
				"Windows 10",
				user
		);
		d4.addVulnerability(v3);
		d4.addVulnerability(v4);

		List<Device> devices =
				Arrays.asList(d1, d2, d3, d4);
		List<Vulnerability> vulnerabilities =
				Arrays.asList(v1, v2, v3, v4, v5);

		user.setEmail("test@test.com");
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode("password"));
		user.setDevices(devices);

		vulnerabilityRepository.saveAll(vulnerabilities);

		userRepository.save(user);
	}
}

package com.github.archturion64.thirdeye.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "device",
        uniqueConstraints = {
        @UniqueConstraint(name = "device_name_address_unique", columnNames = {"device_name", "device_address"})
})
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "device_id",
            updatable = false)
    private Long id;

    @Column(
            columnDefinition="TEXT",
            name = "device_name")
    private String name;

    @Column(
            name = "device_address",
            columnDefinition="TEXT",
            nullable = false)
    private String address;

    @Column(
            name = "device_os",
            columnDefinition="TEXT",
            nullable = false)
    private String os;

    public void addVulnerability(Vulnerability vulnerability){
        vulnerabilities.add(vulnerability);
    }

    public void removeVulnerability(Vulnerability vulnerability){
        vulnerabilities.remove(vulnerability);
    }

    @ManyToMany(
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "device_vulnerabilities",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "vulnerability_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Vulnerability> vulnerabilities = new HashSet<>();

    public Device(String name, String address, String os) {
        this.name = name;
        this.address = address;
        this.os = os;
    }
}

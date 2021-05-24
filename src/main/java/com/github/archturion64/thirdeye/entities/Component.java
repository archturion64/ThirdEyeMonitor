package com.github.archturion64.thirdeye.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Component {
    @Id
    private String id;
    private String name;
    private String version;
}

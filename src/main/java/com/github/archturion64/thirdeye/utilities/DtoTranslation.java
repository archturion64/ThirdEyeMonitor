package com.github.archturion64.thirdeye.utilities;

import com.github.archturion64.thirdeye.dtos.DeviceDto;
import com.github.archturion64.thirdeye.dtos.UserDto;
import com.github.archturion64.thirdeye.entities.Device;
import com.github.archturion64.thirdeye.entities.DeviceInfo;
import com.github.archturion64.thirdeye.entities.User;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoTranslation {

    public static UserDto entityToDto(User entity){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto, "id", "devices");
        return dto;
    }

    public static User dtoToEntity(UserDto dto){
        User entity = new User();
        BeanUtils.copyProperties(dto, entity, "id", "devices");
        return entity;
    }

    public static DeviceDto entityToDto(Device entity){
        DeviceDto dto = new DeviceDto();
        dto.setMacAddress(entity.getMacAddress());
        BeanUtils.copyProperties(entity.getInfo(), dto);
        return dto;
    }

    public static Device dtoToEntity(DeviceDto dto){
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(dto, deviceInfo, "macAddress", "vulnerabilities");

        Device entity = new Device();
        entity.setMacAddress(dto.getMacAddress());
        entity.setInfo(deviceInfo);

        return entity;
    }

    public static List<DeviceDto> mapToList(Map<String, DeviceInfo> map){

        return map.entrySet().stream()
                .map(x -> {
                    DeviceDto dto = new DeviceDto();
                    dto.setMacAddress(x.getKey());
                    BeanUtils.copyProperties(x.getValue(), dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}

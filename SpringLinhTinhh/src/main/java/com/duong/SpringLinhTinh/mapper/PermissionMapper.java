package com.duong.SpringLinhTinh.mapper;

import com.duong.SpringLinhTinh.dto.request.PermisionRequest;
import com.duong.SpringLinhTinh.dto.response.PermisionResponse;
import com.duong.SpringLinhTinh.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermisionRequest request);
    //Lấy giá trị fistName gán cho lastName -> trùng nhau
    //@Mapping(source="firstName",target = "lastName")

    PermisionResponse toPermissionResponse(Permission permission);

}

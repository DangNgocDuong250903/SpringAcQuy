package com.duong.SpringLinhTinh.mapper;

import com.duong.SpringLinhTinh.dto.request.PermisionRequest;
import com.duong.SpringLinhTinh.dto.request.RoleRequest;
import com.duong.SpringLinhTinh.dto.response.PermisionResponse;
import com.duong.SpringLinhTinh.dto.response.RoleResponse;
import com.duong.SpringLinhTinh.entity.Permission;
import com.duong.SpringLinhTinh.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    //Lấy giá trị fistName gán cho lastName -> trùng nhau
    //@Mapping(source="firstName",target = "lastName")

    RoleResponse toRoleResponse(Role role);

}

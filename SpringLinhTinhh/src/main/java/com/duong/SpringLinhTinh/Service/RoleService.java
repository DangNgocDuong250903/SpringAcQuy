package com.duong.SpringLinhTinh.Service;

import com.duong.SpringLinhTinh.dto.request.RoleRequest;
import com.duong.SpringLinhTinh.dto.response.RoleResponse;
import com.duong.SpringLinhTinh.mapper.RoleMapper;
import com.duong.SpringLinhTinh.mapper.RoleRepository;
import com.duong.SpringLinhTinh.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions()); // get all permissions by id
        role.setPermissions(new HashSet<>(permissions)); // set permissions to role

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
        //Tao ra 1 list moi voi cac phan tu duoc map tu roles
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}

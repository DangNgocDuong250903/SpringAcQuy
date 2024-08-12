package com.duong.SpringLinhTinh.Service;

import com.duong.SpringLinhTinh.dto.request.PermisionRequest;
import com.duong.SpringLinhTinh.dto.response.PermisionResponse;
import com.duong.SpringLinhTinh.entity.Permission;
import com.duong.SpringLinhTinh.mapper.PermissionMapper;
import com.duong.SpringLinhTinh.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermisionResponse create(PermisionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission =  permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermisionResponse> getAll() {
        var permissions = permissionRepository.findAll();
       return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}

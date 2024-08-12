package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.RoleService;
import com.duong.SpringLinhTinh.dto.request.ApiResponse;
import com.duong.SpringLinhTinh.dto.request.RoleRequest;
import com.duong.SpringLinhTinh.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@RequestParam String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}

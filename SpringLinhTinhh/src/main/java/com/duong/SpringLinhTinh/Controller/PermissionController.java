package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.PermissionService;
import com.duong.SpringLinhTinh.dto.request.ApiResponse;
import com.duong.SpringLinhTinh.dto.request.PermisionRequest;
import com.duong.SpringLinhTinh.dto.response.PermisionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/permission")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermisionResponse> create(@RequestBody PermisionRequest request){
        return ApiResponse.<PermisionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermisionResponse>> getAll(){
        return ApiResponse.<List<PermisionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@RequestParam String permission){
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}

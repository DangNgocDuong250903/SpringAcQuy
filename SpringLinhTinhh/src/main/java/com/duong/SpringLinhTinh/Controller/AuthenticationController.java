package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.AutheticationService;
import com.duong.SpringLinhTinh.dto.request.ApiResponse;
import com.duong.SpringLinhTinh.dto.request.AuthenticationRequest;
import com.duong.SpringLinhTinh.dto.response.AuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AutheticationService autheticationService;
    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        var result= autheticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}

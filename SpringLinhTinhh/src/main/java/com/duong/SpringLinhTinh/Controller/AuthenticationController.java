package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.AutheticationService;
import com.duong.SpringLinhTinh.dto.request.*;
import com.duong.SpringLinhTinh.dto.response.AuthenticationResponse;
import com.duong.SpringLinhTinh.dto.response.introspecResponse;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AutheticationService autheticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request) {
        var result = autheticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = autheticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<introspecResponse> authentication(@RequestBody introspecRequest request)
            throws ParseException, JOSEException {
        var result = autheticationService.introspect(request);
        return ApiResponse.<introspecResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        autheticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }


}

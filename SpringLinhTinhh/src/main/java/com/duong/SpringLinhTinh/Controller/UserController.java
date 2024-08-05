package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.UserService;
import com.duong.SpringLinhTinh.dto.request.ApiResponse;
import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.request.UserUpdateRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.duong.SpringLinhTinh.entity.User;
import com.duong.SpringLinhTinh.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserRepository userRepository;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userID}")
    UserResponse getUser(@PathVariable("userID") String userID) {
        return userService.getUser(userID);
    }

    @PutMapping("/{userID}")
    UserResponse updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userID, request);
    }

    @DeleteMapping("/{userID}")
    String deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
        return "User deleted";
    }
}

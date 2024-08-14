package com.duong.SpringLinhTinh.Service;

import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.request.UserUpdateRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.duong.SpringLinhTinh.entity.User;
import com.duong.SpringLinhTinh.enums.Role;
import com.duong.SpringLinhTinh.exception.AppException;
import com.duong.SpringLinhTinh.exception.ErrorCode;
import com.duong.SpringLinhTinh.mapper.RoleRepository;
import com.duong.SpringLinhTinh.mapper.UserMapper;
import com.duong.SpringLinhTinh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        //Ma hoa Pass
        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);  //Da khai bao Bean nay trong SecurityConfig
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Ensure roles are assigned to users
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name()); // Default role
       // user.setRoles(roles);
        try {
            user = userRepository.save(user);
            log.info("User created successfully: {}", user.getUsername());
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }


    public  UserResponse getMyinfo(){
       var context =  SecurityContextHolder.getContext();
      String name= context.getAuthentication().getName();

      User user =  userRepository.findByUsername(name).orElseThrow(
              () -> new AppException(ErrorCode.USER_NOT_EXISTED));
      return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUser");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    //Chi có user có username trùng với username trong token mới được phép xem thông tin
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("In method get User by ID");
                return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }


    public UserResponse updateUser(String userID, UserUpdateRequest request) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userID) {
        userRepository.deleteById(userID);
    }

}

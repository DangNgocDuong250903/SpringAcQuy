package com.duong.SpringLinhTinh.Service;

import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.duong.SpringLinhTinh.entity.User;
import com.duong.SpringLinhTinh.exception.AppException;
import com.duong.SpringLinhTinh.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;
//Tự động nạp UserService vào test class để kiểm tra
    @MockBean
    private UserRepository userRepository;
//Tạo một mock của UserRepository, giúp mô phỏng hành vi của repository
// mà không cần kết nối tới cơ sở dữ liệu thật.
    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(1999, 12, 12);

        request = UserCreationRequest.builder()
                .username("duong")
                .firstName("duong")
                .lastName("nguyen")
                .dob(dob)
                .password("123456")
            .build();

        userResponse = UserResponse.builder()
                .id("sadhaslh3hahsdahsdas")
                .username("duong")
                .firstName("duong")
                .lastName("nguyen")
                .dob(dob)
                .build();

        user = User.builder()
                .id("sadhaslh3hahsdahsdas")
                .username("duong")
                .firstName("duong")
                .lastName("nguyen")
                .dob(dob)
                .build();
    }
    @Test
    void createUserTest_ValidRequest_Sucess() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response= userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("sadhaslh3hahsdahsdas");
        Assertions.assertThat(response.getUsername()).isEqualTo("duong");

        //WHEN
        userService.createUser(request);
    }

     @Test
    void createUser_UserExit_False() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));

        //THEN
        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1002);


//                .isEqualTo(123);
    }
}

package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.UserService;
import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData(){
        request = UserCreationRequest.builder()
            .username("duong")
                .firstName("duong")
                .lastName("nguyen")
                .dob(dob)
            .password("123456")
            .build();
        userResponse = UserResponse.builder()
            .username("duong")
            .build();
    }

    @Test
    void createUser() {
        log.info("Test create user");
    }

}

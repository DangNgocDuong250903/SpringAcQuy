package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.Service.UserService;
import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

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
    }

    @Test
    void createUser_ValidRequest_Sucess() throws Exception {

        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request); //Convert object to JSON

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                .thenReturn(userResponse);


        //WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                        .value("sadhaslh3hahsdahsdas")
                );
        //THEN
    }
      @Test
    void createUser_UserNameInValidRequest_Fail() throws Exception {
        request.setUsername("ac");
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request); //Convert object to JSON

//        Mockito.when(userService.createUser(ArgumentMatchers.any()))
//                .thenReturn(userResponse);

        //WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) //errors: 400
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Username must be at least 3 characters")
                );
        //THEN
    }

}


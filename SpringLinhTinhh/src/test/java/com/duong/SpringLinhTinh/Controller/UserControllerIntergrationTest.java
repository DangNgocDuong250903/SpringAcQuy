/* (C)2024 */
package com.duong.SpringLinhTinh.Controller;

import com.duong.SpringLinhTinh.dto.request.UserCreationRequest;
import com.duong.SpringLinhTinh.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntergrationTest {
    @Container
    static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(
                            DockerImageName.parse("mysql:5.7")
                                    .asCompatibleSubstituteFor("mysql:latest"))
                    .withDatabaseName("test")
                    .withUsername("root")
                    .withPassword("root");

    @DynamicPropertySource
    static void configurationDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.ipa.hibernate.auto", () -> "update");
    }

    @Autowired private MockMvc mockMvc;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request =
                UserCreationRequest.builder()
                        .username("john")
                        .firstName("John")
                        .lastName("Doe")
                        .password("12345678")
                        .dob(dob)
                        .build();

        userResponse =
                UserResponse.builder()
                        .id("cf0600f538b3")
                        .username("john")
                        .firstName("John")
                        .lastName("Doe")
                        .dob(dob)
                        .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        var response =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/users")
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john"))
                        .andExpect(
                                MockMvcResultMatchers.jsonPath("result.firstName").value("John"));

        log.info("Result {0}: " + response.andReturn().getResponse().getContentAsString());
    }

    //    @Test
    //        //
    //    void createUser_usernameInvalid_fail() throws Exception {
    //        // GIVEN
    //        request.setUsername("joh");
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.registerModule(new JavaTimeModule());
    //        String content = objectMapper.writeValueAsString(request);
    //
    //        // WHEN, THEN
    //        mockMvc.perform(MockMvcRequestBuilders.post("/users")
    //                        .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                        .content(content))
    ////                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    //                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1000))
    ////                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username must
    // be at least 4 characters"));
    //        ;
    ////                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be
    // at least 4 characters"));
    //    }
}

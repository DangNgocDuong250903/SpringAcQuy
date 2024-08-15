package com.duong.SpringLinhTinh.dto.request;


import com.duong.SpringLinhTinh.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3,message = "USERNAME_INVALID")
    private String username;
//class: globalException -> attributes  -> min{}
    @Size(min = 6,message = "PASSWORD_INVALID")
    private String password;
    private String firstName;
    private String lastName;


    @DobConstraint( min = 18,message = "INVALID_DOB")
    private LocalDate dob;

}

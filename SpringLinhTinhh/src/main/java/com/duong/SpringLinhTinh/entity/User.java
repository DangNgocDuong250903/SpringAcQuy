package com.duong.SpringLinhTinh.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    //Đảm bảo không có username trùng nhau
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Role> roles;

}

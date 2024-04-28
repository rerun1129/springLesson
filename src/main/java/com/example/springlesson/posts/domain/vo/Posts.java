package com.example.springlesson.users.domain.vo;

import com.example.springlesson.users.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private UserRole role;
}

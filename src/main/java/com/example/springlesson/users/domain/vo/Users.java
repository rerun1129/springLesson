package com.example.springlesson.users.domain.vo;

import com.example.springlesson.users.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    private Long id;
    private String username;
    private String email;
    private String picture;
    private String password;
    private Role role;
}

package com.example.springlesson.users.domain.vo;

import com.example.springlesson.users.domain.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Users {
    private Long id;
    private String username;
    private String email;
    private String picture;
    private String password;
    private Role role;

    private String provider;
    private String providerId;
}

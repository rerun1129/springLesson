package com.example.springlesson.users.domain.dto;

import com.example.springlesson.users.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersManageResponseDto {
    private Long id;
    private String username;
    private String email;
    private String joinPath;
    private String suspendedYn;
    private Role role;
}

package com.example.springlesson.users.domain.dto;

import com.example.springlesson.users.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String suspendedYn;
    private Role role;
}

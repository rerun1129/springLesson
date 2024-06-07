package com.example.springlesson.config.auth.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//필요하면 생성자 관련 어노테이션 붙이기
public class LoginRequestDto {
    String username;
    String password;
}

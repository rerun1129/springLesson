package com.example.springlesson.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"), ADMIN("관리자"), MANAGER("매니저");
    private final String title;
}

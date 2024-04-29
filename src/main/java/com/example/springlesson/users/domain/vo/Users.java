package com.example.springlesson.users.domain.vo;

import com.example.springlesson.users.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    public Users (String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}

package com.example.springlesson.config.auth.dto;

import com.example.springlesson.users.domain.Role;
import com.example.springlesson.users.domain.vo.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class OAuthAttributes {
    private Map <String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId, String nameAttributeKey, Map <String, Object> attributes) {
        return ofGoogle(nameAttributeKey, attributes);
    }

    private static OAuthAttributes ofGoogle (String nameAttributeKey, Map <String, Object> attributes) {
        return new OAuthAttributes (
            attributes,
            nameAttributeKey,
            (String) attributes.get ( "name" ),
            (String) attributes.get ( "email" ),
            (String) attributes.get ( "picture" )
        );
    }

    public Users toUser() {
        return new Users (name, email, picture, Role.GUEST);
    }
}

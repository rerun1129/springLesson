package com.example.springlesson.config.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo{
    private final Map<String, Object> attributes; //oauth2User.getAttributes()에 값을 넣어주기 위함

    @Override
    public String getProvider ( ) {
        return "google";
    }

    @Override
    public String getProviderId ( ) {
        return (String) attributes.get ( "sub" );
    }

    @Override
    public String getEmail ( ) {
        return (String) attributes.get ( "email" );
    }

    @Override
    public String getName ( ) {
        return (String) attributes.get ( "name" );
    }

    @Override
    public String getPicture ( ) {
        return (String) attributes.get ( "picture" );
    }
}

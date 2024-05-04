package com.example.springlesson.config.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{
    private final Map<String, Object> attributes; //oauth2User.getAttributes()에 값을 넣어주기 위함

    @Override
    public String getProvider ( ) {
        return "Kakao";
    }

    @Override
    public String getProviderId ( ) {
        return (( Long ) attributes.get ( "id" )).toString ( );
    }

    @Override
    public String getEmail ( ) {
        Map <String, Object> kakaoAccount = ( Map <String, Object> ) attributes.get ( "kakao_account" );
        return (String) kakaoAccount.get ( "email" );
    }

    @Override
    public String getName ( ) {
        Map <String, Object> properties = ( Map <String, Object> ) attributes.get ( "properties" );
        return (String) properties.get ( "nickname" );
    }

    @Override
    public String getPicture ( ) {
        return null;
    }
}

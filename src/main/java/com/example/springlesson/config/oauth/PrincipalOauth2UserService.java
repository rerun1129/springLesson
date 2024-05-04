package com.example.springlesson.config.oauth;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.config.oauth.provider.GoogleUserInfo;
import com.example.springlesson.config.oauth.provider.KakaoUserInfo;
import com.example.springlesson.config.oauth.provider.NaverUserInfo;
import com.example.springlesson.config.oauth.provider.OAuth2UserInfo;
import com.example.springlesson.users.domain.Role;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser ( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser ( userRequest );
        String provider = userRequest.getClientRegistration ( ).getRegistrationId ( );
        OAuth2UserInfo oAuth2UserInfo = null;
        if( "google".equals ( provider )){
            oAuth2UserInfo = new GoogleUserInfo ( oAuth2User.getAttributes () );
        }else if( "naver".equals ( provider )){
            oAuth2UserInfo = new NaverUserInfo ( ( Map <String, Object> ) oAuth2User.getAttributes ( ).get ( "response" ) );
        }else if( "kakao".equals ( provider )){
            oAuth2UserInfo = new KakaoUserInfo ( oAuth2User.getAttributes ( ) );
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail ();
        String picture = oAuth2UserInfo.getPicture ( );
        String username = provider+"_"+providerId;
        String password = ""; //비밀번호의 의미가 없음
        Optional <Users> optionalUsers = userRepository.findByUsername ( username );

        Users buildUser = Users.builder ( )
                                .username ( username )
                                .email ( email )
                                .password ( password )
                                .picture ( picture )
                                .provider ( provider )
                                .role ( optionalUsers.isPresent () ? optionalUsers.get ( ).getRole ( ) : Role.USER )
                                .providerId ( providerId )
                                .build ( );
        if(optionalUsers.isEmpty ()) userRepository.save ( buildUser );
        return new PrincipalDetails ( buildUser, oAuth2User.getAttributes () );
    }
}

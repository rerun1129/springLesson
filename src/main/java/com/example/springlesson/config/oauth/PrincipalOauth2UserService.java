package com.example.springlesson.config.oauth;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser ( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser ( userRequest );
        String provider = userRequest.getClientRegistration ( ).getRegistrationId ( );//google
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute ( "email" );
        String picture = oAuth2User.getAttribute ( "picture" );
        String username = provider+"_"+providerId;
        String password = ""; //비밀번호의 의미가 없음
        Optional <Users> optionalUsers = userRepository.findByUsername ( username );
        Users buildUser = Users.builder ( )
                                .username ( username )
                                .email ( email )
                                .password ( password )
                                .picture ( picture )
                                .provider ( provider )
                                .providerId ( providerId )
                                .build ( );
        if(optionalUsers.isEmpty ()) userRepository.save ( buildUser );
        return new PrincipalDetails ( buildUser, oAuth2User.getAttributes () );
    }
}

package com.example.springlesson.config.auth;

import com.example.springlesson.config.auth.dto.OAuthAttributes;
import com.example.springlesson.config.auth.dto.SessionUser;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser ( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService ( );
        OAuth2User oAuth2User = delegate.loadUser ( userRequest );
        ClientRegistration clientRegistration = userRequest.getClientRegistration ( );
        String registrationId = clientRegistration.getRegistrationId ( );
        String userNameAttributeName = clientRegistration.getProviderDetails ( )
                                                        .getUserInfoEndpoint ( )
                                                        .getUserNameAttributeName ( );
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes ());
        Users user = saveOrUpdate ( attributes );
        httpSession.setAttribute ( "user", new SessionUser (user) );
        return new DefaultOAuth2User ( Collections.singleton (
                new SimpleGrantedAuthority ( user.getRoleKey ())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
            );
    }

    private Users saveOrUpdate ( OAuthAttributes attributes ) {
        Optional <Users> foundUser = userRepository.findByEmail ( attributes.getEmail ( ) );
        boolean isUserPresent = foundUser.isPresent ( );
        if(isUserPresent){
            Users updateUser = foundUser.get ( );
            userRepository.update ( updateUser );
            return updateUser;
        }else {
            Users saveUser = attributes.toUser ( );
            userRepository.save ( saveUser );
            return saveUser;
        }
    }
}

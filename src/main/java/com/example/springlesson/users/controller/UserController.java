package com.example.springlesson.users.controller;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users/test")
    public String testLogin ( Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails ){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails.getUser());
        System.out.println(userDetails.getUser());
        return "일반 로그인 정보 확인";
    }

    @GetMapping("/users/oauth/test")
    public String testOAuthLogin ( Authentication authentication, @AuthenticationPrincipal OAuth2User userDetails ){
        OAuth2User oAuth2User = ( OAuth2User ) authentication.getPrincipal ( );
        System.out.println(oAuth2User.getAttributes ());
        System.out.println(userDetails.getAttributes ());
        return "SNS 로그인 정보 확인";
    }
}

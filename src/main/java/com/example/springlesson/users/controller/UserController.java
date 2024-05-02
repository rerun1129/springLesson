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
    public String testLogin ( @AuthenticationPrincipal PrincipalDetails userDetails ){
        System.out.println(userDetails.getUser());
        return "일반 로그인 정보 확인";
    }

    @GetMapping("/users/oauth/test")
    public String testOAuthLogin ( @AuthenticationPrincipal OAuth2User userDetails ){
        System.out.println(userDetails.getAttributes ());
        return "SNS 로그인 정보 확인";
    }
}

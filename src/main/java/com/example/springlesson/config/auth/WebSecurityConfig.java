package com.example.springlesson.config.auth;

import com.example.springlesson.users.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.csrf().disable()
            .authorizeRequests ()
            .antMatchers ( "/", "/css/**", "/images/**", "/js/**" ).permitAll ()
            .antMatchers ( "/api/**" ).hasRole ( Role.USER.name () )
            .anyRequest ().authenticated ()
            .and ().logout ().logoutSuccessUrl ( "/" )
            .and().oauth2Login ().userInfoEndpoint ().userService ( customOAuth2UserService );
    }
}

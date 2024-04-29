package com.example.springlesson.config.auth;

import com.example.springlesson.config.auth.dto.SessionUser;
import com.example.springlesson.users.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpSession httpSession;
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.csrf().disable()
            .authorizeRequests ()
            .antMatchers ( "/", "/css/**", "/images/**", "/js/**" ).permitAll ()
            .antMatchers ( "/api/**" ).hasRole ( Role.USER.name ( ) )
            .anyRequest ().authenticated ()
            .and ()
            .logout ()
            .logoutSuccessUrl ( "/" )
            .invalidateHttpSession ( true )
            .and().oauth2Login ().defaultSuccessUrl ( "/" ).userInfoEndpoint ().userService ( customOAuth2UserService );
    }
}

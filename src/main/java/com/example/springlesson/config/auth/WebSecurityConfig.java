package com.example.springlesson.config.auth;

import com.example.springlesson.config.auth.filter.JwtAuthenticationFilter;
import com.example.springlesson.config.auth.filter.JwtAuthorizationFilter;
import com.example.springlesson.config.oauth.PrincipalOauth2UserService;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final AuthenticationFailureHandler customFailureHandler;
    private final CorsConfig corsConfig;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder ();
    }

    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.csrf().disable();
        http.addFilter(corsConfig.corsFilter())
            .sessionManagement().sessionCreationPolicy ( SessionCreationPolicy.STATELESS )
            .and().formLogin().disable()
            .httpBasic().disable()
            .addFilter (new JwtAuthenticationFilter ( authenticationManager ( )) )
            .addFilter (new JwtAuthorizationFilter ( authenticationManager ( ), userRepository) )
            .authorizeRequests()
            .antMatchers("/posts/**").authenticated() //인증(로그인)만 하면 접근 가능
            .antMatchers("/admin/manage/customer")//인증 + 역할
            .hasAnyAuthority ( "ADMIN" ) //권한 명을 그대로 읽어서 처리한다.
            .antMatchers("/admin/**")//인증 + 역할
            .hasAnyAuthority ( "ADMIN", "MANAGER" )
            //권한 명을 그대로 읽어서 처리한다.
//            .access("hasRole('ROLE_ADMIN')") //access, hasRole 등의 메서드는 특정 처리를 하지 않았을 때 권한 이름이 ROLE_로 시작하지 않으면 인증 관련 오류가 발생한다.
            .anyRequest().permitAll()
            //기타 URL은 전부 허용(로그인 안해도 접근 가능)
            .and().oauth2Login ().loginPage ( "/loginForm" )
            .failureHandler ( customFailureHandler )
            .userInfoEndpoint ().userService ( principalOauth2UserService );
    }
}

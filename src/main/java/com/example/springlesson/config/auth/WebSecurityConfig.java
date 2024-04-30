package com.example.springlesson.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder ();
    }

    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/posts/**").authenticated() //인증(로그인)만 하면 접근 가능
            .antMatchers("/admin/**")//인증 + 역할
            .access("hasRole('ADMIN')")
            .anyRequest().permitAll()//기타 URL은 전부 허용(로그인 안해도 접근 가능)
            .and().formLogin().loginPage("/loginForm")
            .loginProcessingUrl("/loginProc")
            .defaultSuccessUrl("/");
    }
}

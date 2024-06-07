package com.example.springlesson.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration ();
        config.setAllowCredentials(true);
        //내 서버가 응답을 할 때 json을 js에서 처리할 수 있게 할지를 설정하는 것
        //예를 들어 js의 ajax나 axios가 백엔드 서버로 요청을 하게 되면
        //해당 설정이 true여야 js가 응답을 받을 수 있게 된다.
        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용
        config.addAllowedHeader("*"); //모든 헤더에 응답을 허용
        config.addAllowedMethod("*"); //모든 HTTP 메서드 타입 요청에 응답을 허용

        source.registerCorsConfiguration("/api/**", config);
        //해당 Uri로 오는 요청은 모두 해당 필터 설정을 지나야 함
        return new CorsFilter(source);
    }

}
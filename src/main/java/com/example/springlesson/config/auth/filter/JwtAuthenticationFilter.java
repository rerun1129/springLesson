package com.example.springlesson.config.auth.filter;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.config.auth.filter.dto.LoginRequestDto;
import com.example.springlesson.config.auth.filter.property.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //일반적으로 로그인 요청 시 UsernamePasswordAuthenticationFilter가 동작함
    //하지만 configure 메서드에서 formLogin을 disable했기 때문에 이렇게 따로 호출해줘야 함
    private final AuthenticationManager authenticationManager;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 url => /login
    @Override
    public Authentication attemptAuthentication ( HttpServletRequest request, HttpServletResponse response ) {
        //인증 시도 메서드
        System.out.println("JwtAuthenticationFilter : 진입");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        LoginRequestDto loginRequestDto = new LoginRequestDto (username, password);
        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(UsernamePasswordAuthenticationToken의 첫 파라미터)
        // 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라미터(credential)와
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication:"+principalDetailis.getUser().getUsername());
        return authentication;
    }

    // JWT Token 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        //인증 성공 시 동작 메서드
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
        System.out.println ( "동작 확인" );
        String jwtToken = JWT.create()
                             .withSubject(principalDetailis.getUsername())
                             .withExpiresAt (new Date ( System.currentTimeMillis ( ) + JwtProperties.EXPIRATION_TIME.getMilliseconds ()) )
                             .withClaim("id", principalDetailis.getUser().getId())
                             .withClaim("username", principalDetailis.getUser().getUsername())
                             .withClaim("role", principalDetailis.getUser().getRole ().name ())
                             .sign(Algorithm.HMAC512(JwtProperties.SECRET.getStr ()));

        response.addHeader(JwtProperties.HEADER_STRING.getStr (), JwtProperties.TOKEN_PREFIX.getStr ()+jwtToken);
//        request.getSession().setAttribute(JwtProperties.HEADER_STRING.getStr (), JwtProperties.TOKEN_PREFIX.getStr ()+jwtToken);
//        response.sendRedirect ( "/" );
    }
}

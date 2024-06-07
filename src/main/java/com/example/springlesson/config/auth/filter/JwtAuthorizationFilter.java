package com.example.springlesson.config.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.config.auth.filter.property.JwtProperties;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter( AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
// BasicAuthenticationFilter는 권한이나 인증에 필요한 특정 주소를 요청했을 때
// 해당 필터를 타게 되어있다
// 만약에 권한 인증이 필요한 주소가 아니라면 해당 필터를 타지 않는다.
    protected void doFilterInternal ( HttpServletRequest request,
                                      HttpServletResponse response, FilterChain chain )
    throws IOException, ServletException {
        String header = request.getHeader ( JwtProperties.HEADER_STRING.getStr () );
        //헤더 여부 체크
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX.getStr ())) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("header : "+header);
        String token = request.getHeader(JwtProperties.HEADER_STRING.getStr ())
                              .replace(JwtProperties.TOKEN_PREFIX.getStr (), "");
        // 토큰의 프리픽스를 날리고 순수 토큰만 떼어낸다.

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 직접 접근해서 세션을 만들때
        // 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
        String username = JWT.require ( Algorithm.HMAC512 ( JwtProperties.SECRET.getStr () ) )
                             .build().verify(token)
                             .getClaim("username").asString();

        if(username != null) {	//인증은 토큰 검증시 끝.
            //만약 인증이 정상 작동하지 않았다면 username은 null로 들어오게 됨
            Users user = userRepository.findByUsername ( username ).orElse ( new Users () );
            //null이 아니어도 DB에서 해당 username으로 select가 되지 않으면 정상 사용자가 아님

            // 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고
            // 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails( user);
            Authentication authentication =
                    //인증 객체는 토큰 사용 시 매니저를 사용할 수 없고 직접 만들어줘야 함
                    //서명이 정상이면 인증 객체를 '강제로' 만들어준다
                    new UsernamePasswordAuthenticationToken (
                            principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 authentication 저장
            SecurityContextHolder.getContext ( ).setAuthentication ( authentication );
        }
        chain.doFilter(request, response);
    }

}

package com.example.springlesson.config.auth.domain;

import com.example.springlesson.users.domain.vo.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {
    private final Users user;
    private Map<String, Object> attributes;

    //일반 로그인
    public PrincipalDetails ( Users user ) {
        if(user.getProvider () == null){
            this.user = Users.builder ( )
                            .id ( user.getId () )
                            .username ( user.getUsername () )
                            .email ( user.getEmail () )
                            .password ( user.getPassword () )
                            .role ( user.getRole () )
                            .build ( );
        }else {
            this.user = user;
        }
    }

    //OAuth2 로그인 시 사용
    public PrincipalDetails(Users user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    public Users getUser ( ) {
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//계정 잠금
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//비밀번호 만료
        return true;
    }

    @Override
    public boolean isEnabled() {//계정 활성화
        return true;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection <GrantedAuthority> collect = new ArrayList <> ( );
        collect.add (()-> user.getRole ( ).name ());
        return collect;
    }

    //OAuth2User 의 오버라이드
    @Override
    public String getName ( ) {
        return null;
    }

    @Override
    public Map <String, Object> getAttributes ( ) {
        return attributes;
    }
}

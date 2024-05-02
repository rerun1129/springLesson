package com.example.springlesson.config.auth.domain;

import com.example.springlesson.users.domain.vo.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
    private final Users user;

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
}

package com.example.springlesson.config.auth.service;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        Optional <Users> user = userRepository.findByUsername ( username );
        if(user.isPresent()){
            if("Y".equals ( user.get ().getSuspendedYn () )){
                throw new AuthenticationCredentialsNotFoundException("");
            }else {
                return user.map ( PrincipalDetails::new ).get ();
            }
        } else {
            throw new UsernameNotFoundException("");
        }
    }
}

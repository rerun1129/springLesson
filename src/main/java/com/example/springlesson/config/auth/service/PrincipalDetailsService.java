package com.example.springlesson.config.auth.service;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        return userRepository.findByUsername ( username ).map ( PrincipalDetails::new ).orElse ( null );
    }
}

package com.example.springlesson.users.service;

import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save ( Users user ) {
        String rawPassword = user.getPassword ( );
        String encodePassword = bCryptPasswordEncoder.encode ( rawPassword );
        userRepository.save ( Users.builder ( ).username ( user.getUsername ( ) ).email ( user.getEmail () ).password ( encodePassword ).build ( ) );
    }
}

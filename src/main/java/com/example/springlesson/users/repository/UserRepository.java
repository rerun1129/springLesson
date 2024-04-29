package com.example.springlesson.users.repository;

import com.example.springlesson.users.domain.vo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<Users> findByEmail(String email);

    void save ( Users saveUser );

    void update ( Users foundUser );
}

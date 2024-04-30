package com.example.springlesson.users.repository;

import com.example.springlesson.users.domain.vo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername ( String username );

    void save ( Users user );

    void update ( Users foundUser );

}

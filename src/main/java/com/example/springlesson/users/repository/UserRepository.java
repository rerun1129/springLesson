package com.example.springlesson.users.repository;

import com.example.springlesson.users.domain.dto.UserUpdateRequestDto;
import com.example.springlesson.users.domain.dto.UsersManageResponseDto;
import com.example.springlesson.users.domain.vo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    List <UsersManageResponseDto> findAllByManage ( );
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername ( String username );

    UsersManageResponseDto findById ( Long id );

    void save ( Users user );

    void updateAuth ( UserUpdateRequestDto dto);

}

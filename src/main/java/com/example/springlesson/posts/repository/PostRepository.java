package com.example.springlesson.posts.repository;

import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.domain.dto.PostSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface PostRepository {
    void save ( PostSaveRequestDto dto );
    Optional<PostResponseDto> findById ( Long id );

    void update ( @Param("title") String title, @Param("contents") String contents, @Param("id")Long id );
}

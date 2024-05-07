package com.example.springlesson.posts.repository;

import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.domain.dto.PostSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {
    Optional<PostResponseDto> findById ( Long id );

    List <PostResponseDto> findAll ( );
    List <PostResponseDto> findPostByPageNum ( @Param ( "offset" ) long offset, @Param ( "pageSize" ) int pageSize );
    int countPost ( );

    void save ( PostSaveRequestDto dto );

    void update ( @Param("title") String title, @Param("contents") String contents, @Param("id")Long id );

    void delete ( Long id );
}

package com.example.springlesson.posts.service;

import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.domain.dto.PostSaveRequestDto;
import com.example.springlesson.posts.domain.dto.PostUpdateRequestDto;
import com.example.springlesson.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void save ( PostSaveRequestDto dto ) {
        postRepository.save(dto);
    }

    public PostResponseDto findById ( Long id ) {
        return postRepository.findById(id).orElseThrow ( () -> new IllegalArgumentException ("해당 게시글이 없습니다.") );
    }

    @Transactional
    public void update ( Long id, PostUpdateRequestDto dto ) {
        postRepository.findById ( id ).orElseThrow ( () -> new IllegalArgumentException ("해당 게시글이 없습니다.") );
        postRepository.update(dto.getTitle (), dto.getContents (), id);
    }
}

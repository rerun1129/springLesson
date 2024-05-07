package com.example.springlesson.posts.service;

import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.domain.dto.PostSaveRequestDto;
import com.example.springlesson.posts.domain.dto.PostUpdateRequestDto;
import com.example.springlesson.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto findById ( Long id ) {
        return postRepository.findById(id).orElseThrow ( () -> new IllegalArgumentException ("해당 게시글이 없습니다.") );
    }

    public Page <PostResponseDto> findPostByPageNum ( Pageable page ) {
        List <PostResponseDto> postByPageNum = postRepository.findPostByPageNum ( page.getOffset (), page.getPageSize () );
        int countPage = postRepository.countPost ( );
        return new PageImpl <> (postByPageNum, page, countPage);
    }

    @Transactional
    public void save ( PostSaveRequestDto dto ) {
        postRepository.save(dto);
    }

    @Transactional
    public void update ( Long id, PostUpdateRequestDto dto ) {
        postRepository.findById ( id ).orElseThrow ( () -> new IllegalArgumentException ("해당 게시글이 없습니다.") );
        postRepository.update(dto.getTitle (), dto.getContents (), id);
    }

    @Transactional
    public void delete ( Long id ) {
        postRepository.delete(id);
    }

}

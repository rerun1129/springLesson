package com.example.springlesson.posts.domain.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String author;
}

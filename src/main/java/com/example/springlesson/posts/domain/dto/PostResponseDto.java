package com.example.springlesson.posts.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private LocalDateTime modifiedDate;
}

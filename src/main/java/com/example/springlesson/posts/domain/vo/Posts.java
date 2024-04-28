package com.example.springlesson.posts.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    private Long id;
    private String title;
    private String contents;
    private String author;
}

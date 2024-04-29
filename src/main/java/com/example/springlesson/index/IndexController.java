package com.example.springlesson.index;

import com.example.springlesson.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostService postService;
    @GetMapping("/")
    public String index( Model model ) {
        model.addAttribute ( "posts", postService.findAll () );
        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate ( @PathVariable Long id, Model model ) {
        model.addAttribute ( "posts", postService.findById (id ) );
        return "posts-update";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}

package com.example.springlesson.index;

import com.example.springlesson.posts.service.PostService;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts")
    public String posts( Model model ) {
        model.addAttribute ( "posts", postService.findAll () );
        return "posts";
    }

    @PostMapping("/join")
    public String join ( Users user ) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }
}

package com.example.springlesson.index;

import com.example.springlesson.posts.service.PostService;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class viewController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public ModelAndView index ( Authentication authentication ) { //루트 템플릿에 인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        ModelAndView modelAndView = new ModelAndView("index");
        if(authentication != null) modelAndView.addObject("username", authentication.getName());
        return modelAndView;
    }

    @GetMapping("/posts")
    public String posts( Model model, Authentication authentication ) {
        model.addAttribute ( "posts", postService.findAll () );
        model.addAttribute ( "username", authentication.getName ());//인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        return "posts";
    }

    @GetMapping("/posts/save")
    public String postsSave( Model model, Authentication authentication ) {
        model.addAttribute ( "posts", postService.findAll () );
        model.addAttribute ( "username", authentication.getName ());//인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate( Model model, @PathVariable Long id ) {
        model.addAttribute ( "posts", postService.findById (id) );
        return "posts-update";
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

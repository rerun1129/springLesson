package com.example.springlesson.index;

import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.service.PostService;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.IntStream;

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
    public String posts ( Model model, Authentication authentication, @PageableDefault(size = 5) Pageable page ) {
        Page <PostResponseDto> postByPageNum = postService.findPostByPageNum ( page );

        model.addAttribute ( "posts", postByPageNum );
        model.addAttribute ( "prev", page.previousOrFirst ().getPageNumber () + 1 );
        model.addAttribute ( "next", page.next ().getPageNumber () + 1 );
        model.addAttribute ( "hasPrev", page.hasPrevious () );
        model.addAttribute ( "hasNext", postByPageNum.hasNext () );
        model.addAttribute ( "totalPageCount", IntStream.range ( 1, postByPageNum.getTotalPages () + 1 ).toArray () );
        model.addAttribute ( "username", authentication.getName ());//인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        return "posts";
    }

    @GetMapping("/posts/save")
    public String postsSave( Model model, Authentication authentication ) {
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

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}

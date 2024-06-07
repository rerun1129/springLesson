package com.example.springlesson.index;

import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.service.PostService;
import com.example.springlesson.users.domain.dto.UsersManageResponseDto;
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

import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String index (Model model, Authentication authentication ) { //루트 템플릿에 인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        if(authentication != null) {
            Users user = ( ( PrincipalDetails ) authentication.getPrincipal ( ) ).getUser ( );
            String role = user.getRole ( ).name ( );
            model.addAttribute ( "username", authentication.getName ( ) );
            model.addAttribute ( "canGoAdmin", "ADMIN".equals ( role ) || "MANAGER".equals ( role ));
        }
        return "index";
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
    public String loginForm(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication){
        if(authentication != null) {
            String role = ( ( PrincipalDetails ) authentication.getPrincipal ( ) ).getUser ( ).getRole ( ).name ( );
            model.addAttribute ( "isAdmin", "ADMIN".equals ( role ) );
        }
        return "admin";
    }

    @GetMapping("/admin/manage/customer")
    public String customerManage(Model model){
        model.addAttribute ( "users", userService.findAllByManage() );
        return "customer-manage";
    }

    @GetMapping("/admin/manage/customer/{id}")
    public String customerManageById ( Model model, @PathVariable Long id ){
        UsersManageResponseDto dto = userService.findById ( id );
        model.addAttribute ( "user", dto );
        model.addAttribute ( "isManager", "MANAGER".equals ( dto.getRole ().name () ) );
        model.addAttribute ( "isSuspended", "비활성화".equals ( dto.getSuspendedYn () ) );
        return "customer-manage-update";
    }

}

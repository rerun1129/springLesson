package com.example.springlesson.index;

import com.auth0.jwt.JWT;
import com.example.springlesson.config.auth.domain.PrincipalDetails;
import com.example.springlesson.config.auth.filter.property.JwtProperties;
import com.example.springlesson.config.auth.util.JwtUtil;
import com.example.springlesson.posts.domain.dto.PostResponseDto;
import com.example.springlesson.posts.service.PostService;
import com.example.springlesson.users.domain.dto.UsersManageResponseDto;
import com.example.springlesson.users.domain.vo.Users;
import com.example.springlesson.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String index (Model model, HttpSession session ) { //루트 템플릿에 인증 시 username을 넘겨서 로그인 분기를 취해주기 위함
        if(session.getAttribute ( "Authorization" ) != null) {
            String token = ((String) session.getAttribute ( "Authorization" )).replace ( JwtProperties.TOKEN_PREFIX.getStr ( ), "" );
            try {
                Map<String, Object> payload = JwtUtil.decodeJWT ( token );
                model.addAttribute ( "username", payload.get ( "username" ) );
                String role = (String) payload.get ( "role" );
                model.addAttribute ( "canGoAdmin", "ADMIN".equals ( role ) || "MANAGER".equals ( role ));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public String admin(Model model, HttpSession session){
        if(session.getAttribute ( "Authorization" ) != null) {
            String token = ((String) session.getAttribute ( "Authorization" )).replace ( JwtProperties.TOKEN_PREFIX.getStr ( ), "" );
            try {
                Map<String, Object> payload = JwtUtil.decodeJWT ( token );
                String role = (String) payload.get ( "role" );
                model.addAttribute ( "isAdmin", "ADMIN".equals ( role ));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

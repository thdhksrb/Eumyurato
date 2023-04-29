package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.config.JWT.JwtTokenProvider;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginJoinController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String main() {
        return "html/main/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("token"); // 세션에서 토큰 정보 제거
        return "redirect:/"; // 로그아웃 후 메인 홈페이지로 이동
    }


    @GetMapping("/login-common")
    public String login() {
        return "html/loginJoin/loginform1";
    }

    @PostMapping("/login-common")
    public String login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session, Model model) {
        CommonMemberDTO commonMemberDTO = userService.login(id, pwd); // 사용자 정보 조회
        if (commonMemberDTO != null) {
            List<String> roles = userService.getUserRoles(commonMemberDTO.getId()); // 사용자의 권한 정보 조회
            String token = jwtTokenProvider.createToken(id, roles); // JWT 토큰 생성
            session.setAttribute("token", token); // HttpSession에 토큰 저장
            model.addAttribute("user", commonMemberDTO);
            model.addAttribute("tokenExpiration", jwtTokenProvider.getExpirationDateFromToken(token)); // 토큰 유효시간 모델에 추가
            return "/html/main/main1"; // main1 페이지로 리다이렉트
        } else {
            return "redirect:/login?error"; // 에러 페이지로 이동
        }
    }
}


/*    @GetMapping("/login-art")
    public String login_art() {
        return "html/loginJoin/loginform2";
    }

    @PostMapping("/login-art")
    public String loginArt(@RequestParam("id") String id,
                           @RequestParam("pwd") String pwd,
                           HttpSession session) {
        ArtistMemberDTO artistMemberDTO = userService.loginArt(id, pwd);
        if (artistMemberDTO != null) {
            session.setAttribute("loginUser", artistMemberDTO);
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/artist")
    public String main3() {
        return "html/main/main3";
    }

    @GetMapping("/login-enter")
    public String login_enter() {
        return "html/loginJoin/loginform3";
    }

    @PostMapping("/login-enter")
    public String loginenter(@RequestParam("id") String id,
                             @RequestParam("pwd") String pwd,
                             HttpSession session) {
        EnterpriseMemberDTO enterpriseMemberDTO = userService.loginenter(id, pwd);
        if (enterpriseMemberDTO != null) {
            session.setAttribute("loginUser", enterpriseMemberDTO);
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/enterprise")
    public String registerPage() {
        return "html/loginJoin/loginForm1"; // 로그인 페이지로 이동
    }

    @GetMapping("/Idfind")
    public String idfind() {
        return "html/loginJoin/Idfind";
    }

    @GetMapping("/Pwfind")
    public String Pwfind() {
        return "html/loginJoin/pwfind";
    }

    @GetMapping("/joinchooes")
    public String joinchooes() {
        return "html/loginJoin/joinChooes";
    }

    @GetMapping("/common-join")
    public String common() {
        return "html/loginJoin/joinForm_1";
    }

    @GetMapping("/artist-join")
    public String artist() {
        return "html/loginJoin/joinForm_2";
    }

    @GetMapping("/enterprise-join")
    public String enterprise() {
        return "html/loginJoin/joinForm_3";
    }


    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        // 세션에서 현재 로그인된 사용자 정보를 가져온다
        CommonMemberDTO user = (CommonMemberDTO) session.getAttribute("user");
        if (user == null) { // 로그인되어있지 않으면 로그인 페이지로 이동
            return "redirect:/login?error";
        } else {
            // 현재 사용자가 가진 권한 정보를 가져와 사용자 객체에 추가
            List<String> roles = userService.getUserRoles(user.getId());
            user.setRoles(roles);
            // 모델에 사용자 정보를 담아 마이페이지 화면을 반환
            model.addAttribute("user", user);
            return "html/loginJoin/mypage";
        }
    }

    // 토큰 정보를 반환하는 API
    @GetMapping("/token")
    @ResponseBody
    public String getToken(HttpSession session) {
        // 세션에서 현재 로그인된 사용자 정보를 가져온다
        CommonMemberDTO user = (CommonMemberDTO) session.getAttribute("user");
        if (user == null) { // 로그인되어있지 않으면 에러 메시지 반환
            return "로그인 후 이용해주세요.";
        } else {
            // 현재 사용자가 가진 권한 정보를 가져와 JWT 토큰을 생성
            List<String> roles = userService.getUserRoles(user.getId());
            String token = jwtTokenProvider.createToken(user.getId(), roles);
            // 생성된 토큰 값 반환
            return "발행된 토큰 값: " + token;
        }
    }*/

    /*    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session) {
        CommonMemberDTO commonMemberDTO = userService.login(id, pwd); // 사용자 정보 조회
        if (commonMemberDTO != null) {
            List<String> roles = userService.getUserRoles(id); // 사용자의 권한 정보 조회
            String token = jwtTokenProvider.createToken(commonMemberDTO.getId(), roles); // JWT 토큰 생성
            commonMemberDTO.setRoles(roles); // 사용자 정보에 권한 정보 추가
            session.setAttribute("user", commonMemberDTO); // HttpSession에 사용자 정보 저장
            return "redirect:/mypage"; // 마이페이지로 이동
        } else {
            return "redirect:/login?error"; // 에러 페이지로 이동
        }
        */




package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class loginJoinController {
    @Autowired
    private UserService userService;

    @GetMapping("/root")
    public String root() {return "html/root";}

    @GetMapping("/")
    public String main1(Model model) {
/*        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // token에 저장되어 있는 인증된 사용자의 id 값

        CommonMemberDTO commonMemberDTO = userService.getId(id);
        commonMemberDTO.setPwd(null);  // password는 보이지 않도록 null로 설정
        model.addAttribute("user", commonMemberDTO);*/
        return "html/main/home";
    }

    @GetMapping("/home")
    public String main2() {
        return "html/main/main1";
    }

    @GetMapping("/login")
    public String registerPage() {
/*        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "/login";*/
        return "html/loginJoin/loginForm1";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("pwd") String pwd,
                        HttpSession session) {
        CommonMemberDTO commonMemberDTO = userService.login(id, pwd);
        if (commonMemberDTO != null) {
            session.setAttribute("loginUser", commonMemberDTO);
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/login_art")
    public String login_art() {
        return "html/loginJoin/loginform2";
    }

    @PostMapping("/login_art")
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

    @GetMapping("/login_enter")
    public String login_enter() {
        return "html/loginJoin/loginform3";
    }

    @PostMapping("/login_enter")
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

    @GetMapping("/common")
    public String common() {
        return "html/loginJoin/joinForm_1";
    }

    @GetMapping("/artist")
    public String artist() {
        return "html/loginJoin/joinForm_2";
    }

    @GetMapping("/enterprise")
    public String enterprise() {
        return "html/loginJoin/joinForm_3";
    }

}


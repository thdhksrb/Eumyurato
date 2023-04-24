package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.MemberDTO;
import com.e114.e114_eumyuratodemo1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginJoinController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/root")
    public String root() {return "html/root";}

    @GetMapping("/")
    public String main1() {
        return "main1";
    }

    @GetMapping("/home")
    public String main2() {
        return "main2";
    }

    @GetMapping("/login")
    public String login() {
        return "html/loginJoin/loginForm";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String id, @RequestParam String password, Model model) {
        MemberDTO member = memberService.login(id, password);
        if (member != null) {
            model.addAttribute("member", member);
            return "redirect:/home"; // 로그인 성공 시 홈페이지로 리다이렉트
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "html/loginJoin/loginForm"; // 로그인 실패 시 로그인 폼 페이지 리턴
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


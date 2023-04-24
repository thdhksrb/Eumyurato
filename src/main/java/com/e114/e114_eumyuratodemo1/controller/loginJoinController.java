package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginJoinController {
    @Autowired
    private UserService userService;

    @GetMapping("/root")
    public String root() {return "html/root";}

    @GetMapping("/")
    public String main1() {
        return "home";
    }

    @GetMapping("/home")
    public String main2() {
        return "html/main/main1";
    }

    @GetMapping("/login")
    public String login() {
        return "html/loginJoin/loginForm1";
    }

    @GetMapping("/login_art")
    public String login_art() {
        return "html/loginJoin/loginform2";
    }

    @GetMapping("/login_enter")
    public String login_enter() {
        return "html/loginJoin/loginform3";
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


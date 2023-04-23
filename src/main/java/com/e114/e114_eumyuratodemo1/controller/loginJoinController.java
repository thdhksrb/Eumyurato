package com.e114.e114_eumyuratodemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginJoinController {
    @GetMapping("/root")
    public String root() {return "html/root";}

    @GetMapping("/main")
    public String main() {
        return "main1";
    }

    @GetMapping("/login")
    public String login() {
        return "html/loginJoin/loginForm";
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


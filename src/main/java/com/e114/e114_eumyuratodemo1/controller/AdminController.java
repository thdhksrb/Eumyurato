package com.e114.e114_eumyuratodemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @GetMapping("/profile/admin/root")
    public String adminRoot(){
        return "html/profile/root/profile_admin_root";
    }

    @PostMapping("/profile/admin/root")
    public String adminRootPost(){


        return "redirect:/board/admin";
    }

    @GetMapping("/profile/admin")
    public String adminAccount(){
        return "html/profile/account/profile_admin_account";
    }

    @GetMapping("/profile/admin/modify")
    public String adminAccountModify(){
        return "html/profile/accountModify/profile_admin_accountModify";
    }

    @GetMapping("/profile/admin/management")
    public String adminAccountManagement(){
        return "html/profile/concertManagement/profile_admin_concertmanagement";
    }

    @GetMapping("/profile/admin/register")
    public String adminAccountRegister(){
        return "html/profile/concertRegister/profile_admin_concertRegister";
    }


}

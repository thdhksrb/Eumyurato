package com.e114.e114_eumyuratodemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonController {

    @GetMapping("/profile/common/account")
    public String commontAccount(){
        return "html/profile/account/profile_common_account";
    }

    @PostMapping("/profile/common/account")
    public String commonAccountPost(){
        // POST 요청에 대한 처리 로직 작성
        return "html/profile/account/profile_common_account";
    }

    @GetMapping("/profile/common/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_common_accountModify";
    }

    @GetMapping("/profile/common/management")
    public String artistAccountManagement(){
        return "html/profile/concertManagement/profile_enterprise_concertmanagement";
    }
}

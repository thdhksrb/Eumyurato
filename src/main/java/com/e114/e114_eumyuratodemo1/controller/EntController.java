package com.e114.e114_eumyuratodemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntController {

    @GetMapping("/profile/ent/account")
    public String artistAccount(){
        return "html/profile/account/profile_enterprise_account";
    }

    @GetMapping("/profile/ent/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_enterprise_accountModify";
    }

    @GetMapping("/profile/ent/reservation")
    public String commonReservationList() {

        return "html/profile/reservation/profile_enterprise_reservation";
    }

    @GetMapping("/profile/ent/management")
    public String artistAccountManagement(){
        return "html/profile/concertManagement/profile_enterprise_concertmanagement";
    }
}

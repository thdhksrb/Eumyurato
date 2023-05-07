package com.e114.e114_eumyuratodemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArtistController {

    @GetMapping("/profile/artist/account")
    public String artistAccount(){
        return "html/profile/account/profile_artist_account";
    }

    @GetMapping("/profile/artist/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_artist_accountModify";
    }

    @GetMapping("/profile/artist/reservation")
    public String commonReservationList() {

        return "html/profile/reservation/profile_artist_reservation";
    }

    @GetMapping("/profile/artist/management")
    public String artistAccountManagement(){
        return "html/profile/concertManagement/profile_artist_concertmanagement";
    }

    @GetMapping("/profile/artist/register")
    public String artistAccountRegister(){
        return "html/profile/concertRegister/profile_artist_concertRegister";
    }
}

package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommonController {

    @GetMapping("/profile/common/account")
    public String commontAccount(){
        return "html/profile/account/profile_common_account";
    }

    @GetMapping("/profile/common/modify")
    public String commonAccountModify(){
        return "html/profile/accountModify/profile_common_accountModify";
    }

    @GetMapping("/profile/common/reservation")
    public String commonReservationList() {

        return "html/profile/reservation/profile_common_reservation";
    }
}

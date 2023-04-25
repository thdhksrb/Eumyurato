package com.e114.e114_eumyuratodemo1.controller;


import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private MemberDAO memberDAO;

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

    @GetMapping("/profile/admin/gender")
    public String list(Model model){
        List<ArtistMemberDTO> artists = memberDAO.getArtistMembers();
        List<CommonMemberDTO> commons = memberDAO.getCommonMembers();

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/gender/profile_admin_gender";
    }
    @GetMapping("/profile/admin/gender/search")
    public String search(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);
        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/gender/profile_admin_gender";
    }


}

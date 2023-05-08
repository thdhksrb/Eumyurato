package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EntController {

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/profile/ent/account")
    public String artistAccount(){
        return "html/profile/account/profile_enterprise_account";
    }

    @GetMapping("/profile/ent/data")
    @ResponseBody
    public ResponseEntity<?> getArtistData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String entUserId = jwtUtils.getId(token);
        System.out.println("id : " + entUserId);

        if (entUserId != null) {
            // ID를 이용해 관리자 정보를 가져옵니다.
            EnterpriseMemberDTO common = enterpriseMemberDAO.getEntInfoById(entUserId);
            if (common != null) {
                return ResponseEntity.ok(common);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }

    @GetMapping("/profile/ent/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_enterprise_accountModify";
    }

    @GetMapping("/profile/ent/reservation")
    public String commonReservationList() {

        return "html/profile/reservation/profile_enterprise_reservation";
    }

    @GetMapping("/profile/ent/management/view")
    public String entSmallConcertManagement() {

        return "html/profile/concertManagement/profile_enterprise_concertmanagement";
    }

    @GetMapping("/profile/ent/management")
    public ResponseEntity<?> getSmallConcertList(HttpServletRequest request,
                                            @RequestParam(value = "column", required = false) String column,
                                            @RequestParam(value = "keyword", required = false) String keyword) {

        String token = jwtUtils.getAccessToken(request);
        String smallConcertId = jwtUtils.getId(token);

        List<SmallConcertDTO> smallConcertList;

        if (column != null && keyword != null) {
            smallConcertList = enterpriseService.searchEntSmallConcert(smallConcertId, column, keyword);
        } else {
            smallConcertList = enterpriseService.viewEntSmallConcert(smallConcertId);
        }

        System.out.println("SmallConcert List: " + smallConcertList);

        return ResponseEntity.ok(smallConcertList);
    }
}

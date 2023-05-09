package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/profile/ent/total/view")
    public String enterpriseTotalsview() {

        return "html/profile/total/profile_enterprise_total";
    }

    @GetMapping("/profile/ent/info/view")
    public String enterpriseInfoview() {

        return "html/profile/borard/profile_enterprise_borad";
    }

    @GetMapping("/profile/ent/total")
    public ResponseEntity<?> getMemberList(@RequestParam("category") String category,
                                           @RequestParam(value = "column", required = false) String column,
                                           @RequestParam(value = "keyword", required = false) String keyword) {
        List<?> memberList;

        if (column != null && keyword != null) {
            switch (category) {
                case "common":
                    memberList = enterpriseService.searchCommons(column, keyword);
                    break;
                case "artist":
                    memberList = enterpriseService.searchArtists(column, keyword);
                    break;
                case "enterprise":
                    memberList = enterpriseService.searchEnters(column, keyword);
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        } else {
            switch (category) {
                case "common":
                    memberList = enterpriseService.viewAllCommons();
                    break;
                case "artist":
                    memberList = enterpriseService.viewAllArtists();
                    break;
                case "enterprise":
                    memberList = enterpriseService.viewAllEnters();
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        }

        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/profile/ent/total/commonMember")
    @ResponseBody
    public Map<String, Object> getCommonMember() {

        List<Map<String, Object>> genderCounts = enterpriseService.commonGenderCount();
        List<Map<String, Object>> genreCounts = enterpriseService.commonGenreCount();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);

        return resultMap;
    }

    @GetMapping("/profile/ent/total/artistMember")
    @ResponseBody
    public Map<String, Object> getArtistMember() {

        List<Map<String, Object>> genderCounts = enterpriseService.artistGenderCount();
        List<Map<String, Object>> genreCounts = enterpriseService.artistGenreCount();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);

        return resultMap;
    }

    @GetMapping("/profile/ent/total/enterMember")
    @ResponseBody
    public Map<String, Object> getEnterMember() {

        List<Map<String, Object>> concertIng = enterpriseService.enterConcertIng();
        List<Map<String, Object>> concertAll = enterpriseService.enterConcertAll();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("concertIng", concertIng);
        resultMap.put("concertAll", concertAll);

        return resultMap;
    }

    @GetMapping("/profile/ent/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_enterprise_accountModify";
    }

    //기업회원 정보 수정 처리
    @PostMapping("/profile/ent/modify")
    public ResponseEntity<?> updateEnterMember(@RequestPart("enterDTO") EnterpriseMemberDTO enterpriseMemberDTO, @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {

        if(imgFile == null){
            System.out.println(enterpriseMemberDTO);
            enterpriseService.modifyEnterWithoutImage(enterpriseMemberDTO);
        }else{
            System.out.println(enterpriseMemberDTO);
            enterpriseService.enterModify(enterpriseMemberDTO, imgFile);
        }

        return ResponseEntity.ok().build();
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
        String enterId = jwtUtils.getId(token);

        List<SmallConcertDTO> smallConcertList;

        if (column != null && keyword != null) {
            smallConcertList = enterpriseService.searchEntSmallConcert(enterId, column, keyword);
        } else {
            smallConcertList = enterpriseService.viewEntSmallConcert(enterId);
        }

        System.out.println("SmallConcert List: " + smallConcertList);

        return ResponseEntity.ok(smallConcertList);
    }
}

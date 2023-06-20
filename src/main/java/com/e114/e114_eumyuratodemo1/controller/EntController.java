package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.dao.EnterpriseMemberDAO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

//기업 회원 페이지 컨트롤러
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
    public ResponseEntity<?> getEnterpriseData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String entUserId = jwtUtils.getId(token);
        System.out.println("id : " + entUserId);

        if (entUserId != null) {
            // ID를 이용해 기업회원 정보를 가져오기
            EnterpriseMemberDTO enter = enterpriseMemberDAO.getEntInfoById(entUserId);
            if (enter != null) {
                return ResponseEntity.ok(enter);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }

    @GetMapping("/profile/ent/info/view")
    public String enterpriseInfoview(Model model) {
        List<InfoDTO> infos =  enterpriseMemberDAO.getInfo();

        model.addAttribute("infos", infos);
        return "html/profile/board/profile_enterprise_board";
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

    @GetMapping("/profile/ent/register")
    public String adminAccountRegister() {

        return "html/profile/concertRegister/profile_enterprise_concertRegister";
    }

    @PostMapping("/profile/ent/register")
    public ResponseEntity<?> concertRegister(@RequestPart("registerDTO") SmallConcertDTO smallConcertDTO, @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {

        System.out.println(smallConcertDTO);
        System.out.println("controller img:" + imgFile);

        String name = smallConcertDTO.getName();
        int price = smallConcertDTO.getPrice();
        String startDate = smallConcertDTO.getStartDate();
        String lastDate = smallConcertDTO.getLastDate();
        if (imgFile == null) {
            enterpriseService.saveConcertWithoutImage(smallConcertDTO);
            System.out.println("img null");
        } else {
            enterpriseService.saveConcert(smallConcertDTO, imgFile);
        }

        int conId = enterpriseService.getSmallConcertByAll(name,price,startDate,lastDate + " 23:59:59").getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(lastDate, formatter);

        LocalDate datetime = start;

        while (!datetime.isAfter(end)) {
            enterpriseService.saveSchedules(conId,datetime.toString());
            datetime = datetime.plusDays(1);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/ent/reservation/view")
    public String enterReservationList() {

        return "html/profile/reservation/profile_enterprise_reservation";
    }

    @GetMapping("/profile/ent/reservation")
    public ResponseEntity<?> getCommonReservationList(HttpServletRequest request,
                                                      @RequestParam(value = "column", required = false) String column,
                                                      @RequestParam(value = "keyword", required = false) String keyword) {

        String token = jwtUtils.getAccessToken(request);
        String enterId = jwtUtils.getId(token);

        List<ReservationDTO> reservations;
        if (column != null && keyword != null) {
            reservations = enterpriseService.searchReservationsByEnterId(enterId, column, keyword);
        } else {
            reservations = enterpriseService.getReservationsByEnterId(enterId);
        }

        System.out.println("Reservations: " + reservations);

        return ResponseEntity.ok(reservations);
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

    @DeleteMapping("/profile/ent/management")
    public ResponseEntity<String> deleteSmallConcert(@RequestParam("id") int id) {

        int result = enterpriseService.deleteSmallConcertByEnt(id);

        if (result > 0) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("past");
        }
    }
}

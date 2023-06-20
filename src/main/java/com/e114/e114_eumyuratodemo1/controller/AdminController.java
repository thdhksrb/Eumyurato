package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.dao.AdminMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//관리자 계정 페이지 컨트롤러
@Controller
public class AdminController {

    @Autowired
    private AdminMemberDAO memberDAO;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/profile/admin/account")
    public String adminAccount() {

        return "html/profile/account/profile_admin_account";
    }

    @GetMapping("/profile/admin/data")
    @ResponseBody
    public ResponseEntity<?> getAdminData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String adminUserId = jwtUtils.getId(token);
        System.out.println("id : " + adminUserId);

        if (adminUserId != null) {
            // ID를 이용해 관리자 정보를 가져오기
            EnterpriseMemberDTO admin = memberDAO.getAdminInfoById(adminUserId);
            if (admin != null) {
                return ResponseEntity.ok(admin);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }

    @GetMapping("/profile/admin/modify")
    public String adminAccountModify() {
        return "html/profile/accountModify/profile_admin_accountModify";
    }


    @GetMapping("/profile/admin/total/view")
    public String adminTotalsview() {

        return "html/profile/total/profile_admin_total";
    }

    @GetMapping("/profile/admin/info/view")
    public String admimInfoview(Model model) {
        List<InfoDTO> infos =  memberDAO.getInfo();

        model.addAttribute("infos", infos);

        return "html/profile/board/profile_admin_board";
    }

    @GetMapping("/profile/admin/info/modify")
    public String admimInfomodify() {

        return "html/profile/boardModify/profile_admin_boardModify";
    }

    @GetMapping("/profile/admin/total")
    public ResponseEntity<?> getMemberList(@RequestParam("category") String category,
                                           @RequestParam(value = "column", required = false) String column,
                                           @RequestParam(value = "keyword", required = false) String keyword) {
        List<?> memberList;

        if (column != null && keyword != null) {
            switch (category) {
                case "common":
                    memberList = adminService.searchCommons(column, keyword);
                    break;
                case "artist":
                    memberList = adminService.searchArtists(column, keyword);
                    break;
                case "enterprise":
                    memberList = adminService.searchEnters(column, keyword);
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        } else {
            switch (category) {
                case "common":
                    memberList = adminService.viewAllCommons();
                    break;
                case "artist":
                    memberList = adminService.viewAllArtists();
                    break;
                case "enterprise":
                    memberList = adminService.viewAllEnters();
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        }

        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/profile/admin/total/commonMember")
    @ResponseBody
    public Map<String, Object> getCommonMember() {

        List<Map<String, Object>> genderCounts = adminService.commonGenderCount();
        List<Map<String, Object>> genreCounts = adminService.commonGenreCount();
        List<Map<String, Object>> roadCounts = adminService.commonRoadCount();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);
        resultMap.put("roadCounts", roadCounts);

        return resultMap;
    }

    @GetMapping("/profile/admin/total/artistMember")
    @ResponseBody
    public Map<String, Object> getArtistMember() {

        List<Map<String, Object>> genderCounts = adminService.artistGenderCount();
        List<Map<String, Object>> genreCounts = adminService.artistGenreCount();
        List<Map<String, Object>> points = adminService.artistPointTop();
        List<Map<String, Object>> pointAvg = adminService.artistPointAvg();
        List<Map<String, Object>> buskingIng = adminService.artistBuskingIng();
        List<Map<String, Object>> buskingAll = adminService.artistBuskingAll();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);
        resultMap.put("points", points);
        resultMap.put("pointAvg", pointAvg);
        resultMap.put("buskingIng", buskingIng);
        resultMap.put("buskingAll", buskingAll);

        return resultMap;
    }

    @GetMapping("/profile/admin/total/enterMember")
    @ResponseBody
    public Map<String, Object> getEnterMember() {

        List<Map<String, Object>> concertIng = adminService.enterConcertIng();
        List<Map<String, Object>> concertAll = adminService.enterConcertAll();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("concertIng", concertIng);
        resultMap.put("concertAll", concertAll);

        return resultMap;
    }

    @GetMapping("/profile/admin/reservation/view")
    public String reservationList() {

        return "html/profile/reservation/profile_admin_reservation";
    }

    @GetMapping("/profile/admin/reservation")
    public ResponseEntity<?> getCommonReservationList(@RequestParam(value = "column", required = false) String column,
                                                      @RequestParam(value = "keyword", required = false) String keyword) {

        List<ReservationDTO> reservationList;

        if (column != null && keyword != null) {
            reservationList = adminService.searchReservations(column, keyword);
        } else {
            reservationList = adminService.viewAllReservations();
        }

        System.out.println("Reservation List: " + reservationList);

        return ResponseEntity.ok(reservationList);
    }

    @DeleteMapping("/profile/admin/reservation")
    public ResponseEntity<String> deleteReservation(@RequestParam("id") int id) {

        String seatNum = adminService.findTicketByRid(id).getSeatNum();  //추가
        System.out.println("seatNum: " +seatNum);
        List<String> seatNumList = Arrays.asList(seatNum.split(","));
        System.out.println(seatNumList);
        System.out.println("---------------");
        int schedulesId = adminService.findReservationById(id).getSid();
        int ticketResult = adminService.deleteTicket(id);

        int reservationResult = adminService.deleteReservation(id);
        int result = adminService.deleteBooked(schedulesId,seatNumList);
        System.out.println("result: "+ result);
        if (ticketResult > 0 && reservationResult > 0) { // 삭제 성공
            return ResponseEntity.ok("success");
        } else { // 삭제 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete reservation.");
        }
    }

    @GetMapping("/profile/admin/management/view")
    public String adminAccountManagement() {

        return "html/profile/concertManagement/profile_admin_concertManagement";
    }

    @GetMapping("/profile/admin/management")
    public ResponseEntity<?> getEventList(@RequestParam("category") String category,
                                          @RequestParam(value = "column", required = false) String column,
                                          @RequestParam(value = "keyword", required = false) String keyword) {
        List<?> eventList;

        if (column != null && keyword != null) {
            switch (category) {
                case "busking":
                    eventList = adminService.searchBuskings(column, keyword);
                    break;
                case "localfestival":
                    eventList = adminService.searchLocalFestivals(column, keyword);
                    break;
                case "smallconcert":
                    eventList = adminService.searchSmallConcerts(column, keyword);
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        } else {
            switch (category) {
                case "busking":
                    eventList = adminService.viewAllBusking();
                    break;
                case "localfestival":
                    eventList = adminService.viewAllLocalFestival();
                    break;
                case "smallconcert":
                    eventList = adminService.viewAllSmallConcert();
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        }

        return ResponseEntity.ok(eventList);
    }

    @DeleteMapping("/profile/admin/management")
    public ResponseEntity<Void> deleteConcert(@RequestParam("category") String category, @RequestParam("id") int id) {
        System.out.println(category + "," + id);
        switch (category) {
            case "busking":
                adminService.deleteDonation(id);
                adminService.deleteBusking(id);
                break;
            case "localfestival":
                adminService.deleteLocalFestival(id);
                break;
            case "smallconcert":
                adminService.deleteSmallConcert(id);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

      //  adminService.deleteEvent(category, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/admin/total/chart")
    public String adminGraph() {

        return "html/profile/totalChart/profile_admin_totalChart";
    }

}

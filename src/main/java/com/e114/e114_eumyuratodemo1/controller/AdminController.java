package com.e114.e114_eumyuratodemo1.controller;


import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import com.e114.e114_eumyuratodemo1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminMemberDAO memberDAO;

    @Autowired
    private AdminService adminService;

    @GetMapping("/profile/admin/root")
    public String adminRoot(){
        return "html/profile/root/profile_admin_root";
    }

    @PostMapping("/profile/admin/root")
    public String adminRootPost(){
        return "redirect:/board/admin";
    }

    @GetMapping("/profile/admin/{adminId}")
    public String adminAccount(@PathVariable("adminId") String adminId, Model model){
        EnterpriseMemberDTO admin = memberDAO.getAdminInfoById(adminId);

        model.addAttribute("admin", admin);

        return "html/profile/account/profile_admin_account";
    }

    @GetMapping("/profile/admin/modify")
    public String adminAccountModify(){
        return "html/profile/accountModify/profile_admin_accountModify";
    }

    @GetMapping("/profile/admin/management/view")
    public String adminAccountManagement(){

        return "html/profile/concertManagement/profile_admin_concertmanagement";
    }

    @GetMapping("/profile/admin/register")
    public String adminAccountRegister(){
        return "html/profile/concertRegister/profile_admin_concertRegister";
    }

    @GetMapping("/profile/admin/total")
    public String getCommonsAndArtistsList(Model model){
        List<ArtistMemberDTO> artists = memberDAO.getArtistMembers();
        List<CommonMemberDTO> commons = memberDAO.getCommonMembers();
        List<EnterpriseMemberDTO> ents = memberDAO.getEntMembers();

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);
        model.addAttribute("ents", ents);

        return "html/profile/total/profile_admin_total";
    }

    @PostMapping("/profile/admin/total/search")
    public String searchPostMembers(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);
        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/total/profile_admin_total";
    }

    @GetMapping("/profile/admin/total/search")
    public String searchGetMembers(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);
        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/total/profile_admin_total";
    }

    @PostMapping("/profile/admin/total/entSearch")
    public String searchPostEnts(@RequestParam("column2") String column2, @RequestParam("keyword2") String keyword2, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column2", column2);
        params.put("keyword2", keyword2);

        List<EnterpriseMemberDTO> ents = memberDAO.searchEntMembers(params);

        model.addAttribute("ents", ents);

        return "html/profile/total/profile_admin_total2";
    }

    @GetMapping("/profile/admin/total/entSearch")
    public String searchGetEnts(@RequestParam("column2") String column2, @RequestParam("keyword2") String keyword2, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column2", column2);
        params.put("keyword2", keyword2);

        List<EnterpriseMemberDTO> ents = memberDAO.searchEntMembers(params);

        model.addAttribute("ents", ents);

        return "html/profile/total/profile_admin_total2";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 초기화
        return "redirect:/home";
    }

    @GetMapping("/profile/admin/reservation")
    public String reservationList(Model model) {
        List<ReservationDTO> reservations = memberDAO.getReservationList();
        model.addAttribute("reservations", reservations);

        return "html/profile/reservation/profile_admin_reservation";
    }

    @PostMapping("/profile/admin/reservation/search")
    public String searchReservation(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model){
        Map<String, String > params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ReservationDTO> reservations = memberDAO.searchReservations(params);

        model.addAttribute("reservations", reservations);

        return "html/profile/reservation/profile_admin_reservation";
    }

    @GetMapping("/profile/admin/reservation/search")
    public String searchReservations(){

        return "html/profile/reservation/profile_admin_reservation";
    }

    @GetMapping("/profile/admin/management")
    public ResponseEntity<?> getEventList(@RequestParam("category") String category) {
        List<?> eventList;

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

        return ResponseEntity.ok(eventList);
    }

}

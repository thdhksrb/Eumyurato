package com.e114.e114_eumyuratodemo1.controller;


import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import com.e114.e114_eumyuratodemo1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @PostMapping("/profile/admin/register")
    public ResponseEntity<?> concertRegister(@RequestPart("registerDTO") SmallConcertDTO smallConcertDTO, @RequestPart(value = "imgFile",required = false) MultipartFile imgFile) throws IOException {
        System.out.println(smallConcertDTO);
        System.out.println("controller img:" + imgFile);
        if(imgFile==null){
            adminService.saveConcertWithoutImage(smallConcertDTO);
            System.out.println("img null");
        }
        else {
            adminService.saveConcert(smallConcertDTO, imgFile);
        }
        return ResponseEntity.ok().build();
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

    @PostMapping("/profile/admin/total/commSearch")
    public String searchCommonMembers(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);
        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/total/profile_admin_total";
    }

    @GetMapping("/profile/admin/total/commSearch")
    public String GetCommonMembers(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);
        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("artists", artists);
        model.addAttribute("commons", commons);

        return "html/profile/total/profile_admin_total";
    }

    @PostMapping("/profile/admin/total/artSearch")
    public String searchArtMembers(@RequestParam("column2") String column2, @RequestParam("keyword2") String keyword2, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column2", column2);
        params.put("keyword2", keyword2);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);

        model.addAttribute("artists", artists);

        return "html/profile/total/profile_admin_total2";
    }

    @GetMapping("/profile/admin/total/artSearch")
    public String getArtMembers(@RequestParam("column2") String column2, @RequestParam("keyword2") String keyword2, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column2", column2);
        params.put("keyword2", keyword2);

        List<ArtistMemberDTO> artists = memberDAO.searchArtistMembers(params);

        model.addAttribute("artists", artists);

        return "html/profile/total/profile_admin_total2";
    }

    @PostMapping("/profile/admin/total/entSearch")
    public String searchPostEnts(@RequestParam("column3") String column3, @RequestParam("keyword3") String keyword3, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column3", column3);
        params.put("keyword3", keyword3);

        List<EnterpriseMemberDTO> ents = memberDAO.searchEntMembers(params);

        model.addAttribute("ents", ents);

        return "html/profile/total/profile_admin_total3";
    }

    @GetMapping("/profile/admin/total/entSearch")
    public String searchGetEnts(@RequestParam("column3") String column3, @RequestParam("keyword3") String keyword3, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column3", column3);
        params.put("keyword3", keyword3);

        List<EnterpriseMemberDTO> ents = memberDAO.searchEntMembers(params);

        model.addAttribute("ents", ents);

        return "html/profile/total/profile_admin_total3";
    }

/*    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 초기화
        return "redirect:/home";
    }*/

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
    public ResponseEntity<Void> deleteConcert(@RequestParam("category") String category, @RequestParam("id") int id){
        System.out.println(category +","+ id);
        adminService.deleteEvent(category, id);
        return ResponseEntity.ok().build();
    }

}

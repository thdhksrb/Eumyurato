package com.e114.e114_eumyuratodemo1.controller;


import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import com.e114.e114_eumyuratodemo1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private AdminMemberDAO memberDAO;

    @Autowired
    private AdminService adminService;

    private static String UPLOAD_DIR = "uploads/";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    @GetMapping("/profile/admin/root")
    public String adminRoot(){
        return "html/profile/account/profile_admin_account";
    }

    @GetMapping("/profile/admin/account")
    public String adminAccount(HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();

        String id = request.getParameter("id");

        map.put("id", id);

        memberDAO.getAdminInfoById(map);

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
    public String handleFileUpload(@RequestParam("avatar") MultipartFile file, SmallConcertDTO smallConcertDTO,
                                   RedirectAttributes redirectAttributes) {
        // 파일 저장 경로 설정
        String uploadDirectory = "uploads";
        Path uploadPath = Paths.get(uploadDirectory);

        // 파일 형식 제한 (예: .png, .jpg, .jpeg)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!fileExtension.matches(".png|.jpg|.jpeg")) {
            redirectAttributes.addFlashAttribute("message", "지원하지 않는 파일 형식입니다. (.png, .jpg, .jpeg 만 가능)");
            return "redirect:/profile/admin/register";
        }

        try {
            // 파일 이름 중복 처리
            String uniqueFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + "_" + originalFilename;

            // 파일 저장
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(uniqueFilename);
            file.transferTo(filePath.toFile());

            // 업로드된 파일 경로를 DTO에 설정
            smallConcertDTO.setImage(filePath.toString());

            // DTO를 이용해 공연 정보 저장
            adminService.registerSmallConcert(smallConcertDTO);

        } catch (IOException e) {
            // 파일 업로드 예외 처리
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/profile/admin/register";
        }

        // 처리 후 리다이렉트 또는 반환할 뷰 이름 설정
        return "redirect:/profile/admin/management/view";
    }

//    @PostMapping("/profile/admin/register")
//    public String smallConcertRegister(HttpServletRequest request){
//
//        Map<String, String> map = new HashMap<String, String>();
//        String name = request.getParameter("name");
//        String enterId = request.getParameter("enterId");
//        String location = request.getParameter("location");
//        String pname = request.getParameter("pname");
//        String startDate = request.getParameter("startDate");
//        String lastDate = request.getParameter("lastDate");
//        String price = request.getParameter("price");
//
//        map.put("name", name);
//        map.put("enterId", enterId);
//        map.put("location", location);
//        map.put("pname", pname);
//        map.put("startDate", startDate);
//        map.put("lastDate", lastDate);
//        map.put("price", price);
//
//        memberDAO.registerSmallConcert(map);
//
//        return "redirect:/profile/admin/management/view";
//    }

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

        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

        model.addAttribute("commons", commons);

        return "html/profile/total/profile_admin_total";
    }

    @GetMapping("/profile/admin/total/commSearch")
    public String GetCommonMembers(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);

        List<CommonMemberDTO> commons = memberDAO.searchCommonMembers(params);

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

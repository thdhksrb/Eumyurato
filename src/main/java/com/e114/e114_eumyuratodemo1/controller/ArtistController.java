package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArtistController {

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/profile/artist/account")
    public String artistAccount(){

        return "html/profile/account/profile_artist_account";
    }

    @GetMapping("/profile/artist/data")
    @ResponseBody
    public ResponseEntity<?> getArtistData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String artistUserId = jwtUtils.getId(token);
        System.out.println("id : " + artistUserId);

        if (artistUserId != null) {
            // ID를 이용해 관리자 정보를 가져옵니다.
            ArtistMemberDTO artist = artistMemberDAO.getArtistInfoById(artistUserId);
            if (artist != null) {
                return ResponseEntity.ok(artist);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }

    @GetMapping("/profile/artist/modify")
    public String artistAccountModify(){
        return "html/profile/accountModify/profile_artist_accountModify";
    }

    @GetMapping("/profile/artist/reservation")
    public String artistReservationListPage() {
        return "html/profile/reservation/profile_artist_reservation";
    }

//    @GetMapping("/profile/artist/reservation/data")
//    public ResponseEntity<List<ReservationDTO>> getArtistReservationData(HttpServletRequest request) {
//        // 토큰에서 아티스트 아이디를 가져옵니다.
//        String token = jwtUtils.getAccessToken(request);
//        String artistId = jwtUtils.getId(token);
//
//        // 가져온 아티스트 아이디를 사용하여 예약 목록을 조회합니다.
//        List<ReservationDTO> artistReservations = artistMemberDAO.getArtistReservationList(artistId);
//
//        return ResponseEntity.ok(artistReservations);
//    }
//
//    @PostMapping("/profile/artist/reservation/search")
//    public String searchArtReservation(@RequestParam("column") String column, @RequestParam("keyword") String keyword, Model model, HttpServletRequest request) {
//        // 토큰에서 아티스트 아이디를 가져옵니다.
//        String token = jwtUtils.getAccessToken(request);
//        String artistId = jwtUtils.getId(token);
//
//        Map<String, String> params = new HashMap<>();
//        params.put("column", column);
//        params.put("keyword", keyword);
//        params.put("id", artistId);
//
//        List<ReservationDTO> artReservations = artistMemberDAO.searchArtReservations(params);
//
//        model.addAttribute("reservations", artReservations);
//
//        return "html/profile/reservation/profile_artist_reservation";
//    }
//
//    @GetMapping("/profile/artist/reservation/search")
//    public String searchArtReservations() {
//
//        return "html/profile/reservation/profile_artist_reservation";
//    }

    @GetMapping("/profile/artist/management/view")
    public String artistBuskingManagement() {

        return "html/profile/concertManagement/profile_artist_concertmanagement";
    }

    @GetMapping("/profile/artist/management")
    public ResponseEntity<?> getBuskingList(HttpServletRequest request,
                                          @RequestParam(value = "column", required = false) String column,
                                          @RequestParam(value = "keyword", required = false) String keyword) {

        String token = jwtUtils.getAccessToken(request);
        String artId = jwtUtils.getId(token);

        List<BuskingDTO> buskingList;

        if (column != null && keyword != null) {
            buskingList = artistService.searchArtistBusking(artId, column, keyword);
        } else {
            buskingList = artistService.viewArtistBusking(artId);
        }

        System.out.println("Busking List: " + buskingList);

        return ResponseEntity.ok(buskingList);
    }

    @DeleteMapping("/profile/artist/management")
    public ResponseEntity<String> deleteConcert(@RequestParam("id") int id) {

        int result = artistService.deleteBusking(id);

        if (result > 0) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("past");
        }
    }

    @GetMapping("/profile/artist/register")
    public String artistAccountRegister(){

        return "html/profile/concertRegister/profile_artist_concertRegister";
    }
}

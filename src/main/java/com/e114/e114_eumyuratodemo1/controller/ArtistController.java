package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @GetMapping("/profile/artist/total/view")
    public String artistTotalsview() {

        return "html/profile/total/profile_artist_total";
    }

    @GetMapping("/profile/artist/info/view")
    public String artistInfoview() {

        return "html/profile/board/profile_artist_board";
    }

    @GetMapping("/profile/artist/total")
    public ResponseEntity<?> getMemberList(@RequestParam("category") String category,
                                           @RequestParam(value = "column", required = false) String column,
                                           @RequestParam(value = "keyword", required = false) String keyword) {
        List<?> memberList;

        if (column != null && keyword != null) {
            switch (category) {
                case "common":
                    memberList = artistService.searchCommons(column, keyword);
                    break;
                case "artist":
                    memberList = artistService.searchArtists(column, keyword);
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        } else {
            switch (category) {
                case "common":
                    memberList = artistService.viewAllCommons();
                    break;
                case "artist":
                    memberList = artistService.viewAllArtists();
                    break;
                default:
                    return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
            }
        }

        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/profile/artist/total/commonMember")
    @ResponseBody
    public Map<String, Object> getCommonMember() {

        List<Map<String, Object>> genderCounts = artistService.commonGenderCount();
        List<Map<String, Object>> genreCounts = artistService.commonGenreCount();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);

        return resultMap;
    }

    @GetMapping("/profile/artist/total/artistMember")
    @ResponseBody
    public Map<String, Object> getArtistMember() {

        List<Map<String, Object>> genderCounts = artistService.artistGenderCount(); // 성별 체크
        List<Map<String, Object>> genreCounts = artistService.artistGenreCount();   // 장르 체크
        List<Map<String, Object>> points = artistService.artistPointTop();          // 보유 포인트 체크
        List<Map<String, Object>> pointAvg = artistService.artistPointAvg();        // 포인트 평균
        List<Map<String, Object>> buskingIng = artistService.artistBuskingIng();    // 진행중인 버스킹 목록 가져오기
        List<Map<String, Object>> buskingAll = artistService.artistBuskingAll();    // 모든 버스킹 목록 가져오기

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("genderCounts", genderCounts);
        resultMap.put("genreCounts", genreCounts);
        resultMap.put("points", points);
        resultMap.put("pointAvg", pointAvg);
        resultMap.put("buskingIng", buskingIng);
        resultMap.put("buskingAll", buskingAll);

        return resultMap;
    }

    @GetMapping("/profile/artist/register")
    public String artistAccountRegister(){

        return "html/profile/concertRegister/profile_artist_concertRegister";
    }

    @PostMapping("/profile/artist/register")
    public ResponseEntity<?> buskingRegister(@RequestPart("registerDTO") BuskingDTO buskingDTO, @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException{

        if (imgFile == null) {
            artistService.saveBuskingWithoutImage(buskingDTO);
            System.out.println("img null");
        } else {
            artistService.saveBusking(buskingDTO, imgFile);
        }
        return ResponseEntity.ok().build();
    }


}

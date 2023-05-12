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
            // ID를 이용해 아티스트 정보 가져오기
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

    //아티스트회원 정보 수정 처리
    @PostMapping("/profile/artist/modify")
    public ResponseEntity<?> updateCommonMember(@RequestPart("artistDTO") ArtistMemberDTO artistMemberDTO,  @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {

        if(imgFile == null){
            System.out.println(artistMemberDTO);
            artistService.modifyArtistWithoutImage(artistMemberDTO);
        }else{
            System.out.println(artistMemberDTO);
            artistService.artistModify(artistMemberDTO, imgFile);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/artist/reservation")
    public String artistReservationListPage() {
        return "html/profile/reservation/profile_artist_reservation";
    }

    @GetMapping("/profile/artist/management/view")
    public String artistBuskingManagement() {

        return "html/profile/concertManagement/profile_artist_concertManagement";
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

    @GetMapping("/profile/artist/info/view")
    public String artistInfoview(Model model) {
        List<InfoDTO> infos =  artistMemberDAO.getInfo();

        model.addAttribute("infos", infos);
        return "html/profile/board/profile_artist_board";
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

    @PostMapping("/profile/artist/nidcheck")
    public Map<String,Object> artistNidCheck(@RequestParam("nid") String nid){
        System.out.println(nid);
        Map<String, Object> nidResult = new HashMap<>();
        nidResult.put("nid", artistService.artistNid(nid));
        return nidResult;
    }


}

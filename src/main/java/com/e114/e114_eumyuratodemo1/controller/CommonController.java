package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.mapper.CommonMemberMapper;
import com.e114.e114_eumyuratodemo1.service.CommonService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CommonMemberMapper commonMemberMapper;

    @GetMapping("/profile/common/account")
    public String commontAccount(){
        return "html/profile/account/profile_common_account";
    }

    @GetMapping("/profile/common/data")
    @ResponseBody
    public ResponseEntity<?> getArtistData(HttpServletRequest request) {
        String token = jwtUtils.getAccessToken(request);
        String commonUserId = jwtUtils.getId(token);
        System.out.println("id : " + commonUserId);

        if (commonUserId != null) {
            // ID를 이용해 관리자 정보를 가져옵니다.
            CommonMemberDTO common = commonMemberDAO.getCommonInfoById(commonUserId);
            if (common != null) {
                return ResponseEntity.ok(common);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
    }

    // 회원 정보 수정 폼 요청 처리
    @GetMapping("/profile/common/modify")
    public String commonAccountModify(Model model, HttpServletRequest request) {
        return "html/profile/accountModify/profile_common_accountModify";
    }

    // 회원 정보 수정 처리
    @PostMapping("/profile/common/modify")
    public ResponseEntity<?> updateCommonMember(@RequestPart("commonDTO") CommonMemberDTO commonMemberDTO,  @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {

        if(imgFile == null){
            System.out.println(commonMemberDTO);
            commonService.modifyCommonWithoutImage(commonMemberDTO);
        }else{
            System.out.println(commonMemberDTO);
            commonService.modifyCommon(commonMemberDTO, imgFile);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/common/reservation/view")
    public String getCommonReservations() {

        return "html/profile/reservation/profile_common_reservation";
    }

    @GetMapping("/profile/common/reservation")
    public ResponseEntity<?> getCommonReservationList(HttpServletRequest request,
                                            @RequestParam(value = "column", required = false) String column,
                                            @RequestParam(value = "keyword", required = false) String keyword) {

        String token = jwtUtils.getAccessToken(request);
        String cId = jwtUtils.getId(token);

        List<ReservationDTO> reservationList;

        if (column != null && keyword != null) {
            reservationList = commonService.searchCommonReservations(cId, column, keyword);
        } else {
            reservationList = commonService.viewCommonReservations(cId);
        }

        System.out.println("Reservation List: " + reservationList);

        return ResponseEntity.ok(reservationList);
    }

    @DeleteMapping("/profile/common/reservation")
    public ResponseEntity<String> deleteReservation(@RequestParam("id") int id) {

        int result = commonService.deleteReservation(id);

        if (result > 0) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("past");
        }
    }
}

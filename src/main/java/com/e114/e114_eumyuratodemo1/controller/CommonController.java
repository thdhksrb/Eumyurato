package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//일반회원 페이지 컨트롤러
@Controller
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private JwtUtils jwtUtils;


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
            CommonMemberDTO common = commonService.getCommonInfoById(commonUserId);
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
    public String commonAccountModify() {
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

    @GetMapping("/profile/common/info/view")
    public String commonInfoview(Model model) {
        List<InfoDTO> infos =  commonService.getInfo();

        model.addAttribute("infos", infos);
        return "html/profile/board/profile_common_board";
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


        String seatNum = commonService.findTicketByRid(id).getSeatNum();  //추가
        System.out.println("seatNum: " +seatNum);
        List<String> seatNumList = Arrays.asList(seatNum.split(","));
        System.out.println(seatNumList);
        System.out.println("---------------");
        int schedulesId = commonService.findReservationById(id).getSid();

        int ticketResult = commonService.deleteTicket(id);
        int reservationResult = commonService.deleteReservation(id);

        int result = commonService.deleteBooked(schedulesId,seatNumList);
        System.out.println("result: "+ result);

        if (ticketResult > 0 && reservationResult > 0) { // 삭제 성공
            return ResponseEntity.ok("success");
        } else { // 삭제 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete reservation.");
        }
    }

    @PostMapping("/profile/common/nidcheck")
    public ResponseEntity<String> commonNidCheck(@RequestParam("nid") String nid){
        System.out.println(nid);
        System.out.println(commonService.commonNid(nid));
        if(commonService.commonNid(nid) == 0) { //닉네임이 없는 경우
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate");
        }
    }
}

package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.e114.e114_eumyuratodemo1.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//지도 및 메인페이지 컨트롤러
@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private DataDTO dto;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/map")
    public String smallConcert(){

        return "html/map/concertMap";
    }

    @GetMapping("/all")
    @ResponseBody
    public Map<String,List<?>> all() {

        Map<String,List<?>> map = new HashMap<>();

        List<SmallConcertDTO> smallConcert = mapService.viewSmallConcert();
        List<BuskingDTO> busking = mapService.viewBusking();
        List<LocalFestivalDTO> localFestival = mapService.viewLocalFestival();

        map.put("smallConcert",smallConcert);
        map.put("busking",busking);
        map.put("localFestival",localFestival);

        return map;
    }
    @GetMapping("/smallconcert/detail/{id}")
    public String smallConcertDetail(@PathVariable("id") int id) {

        mapService.upViewCountSmallConcert(id);

        return "html/detail/smallConcertDetail";
    }

    @GetMapping("/smallconcert/detail/{id}/json")
    @ResponseBody
    public SmallConcertDTO smallConcertDetailJson(@PathVariable("id") int id) throws IOException {
        SmallConcertDTO dto = mapService.selectConcert(id);

        return dto;
    }

    @GetMapping("/local_festival/detail/{id}")
    public String localDetail(@PathVariable("id") int id) {

        mapService.upViewCountLocalFestival(id);

        return "html/detail/localDetail";
    }

    @GetMapping("/local_festival/detail/{id}/json")
    @ResponseBody
    public LocalFestivalDTO localDetailJson(@PathVariable("id") int id) {
        LocalFestivalDTO dto = mapService.selectLocal(id);

        return dto;
    }

    @GetMapping("/busking/detail/{id}")
    public String buskingDetail(@PathVariable("id") int id) {

        mapService.upViewCountBusking(id);

        return "html/detail/buskingDetail";
    }

    @GetMapping("/busking/detail/{id}/json")
    @ResponseBody
    public BuskingDTO buskinDetailJson(@PathVariable("id") int id) {
        BuskingDTO dto = mapService.selectBusking(id);
        System.out.println(dto);

        return dto;
    }

    @GetMapping("/smallconcert/detail/{id}/calendar")
    public String calendarPage(){
        return "html/pay/pay1";
    }

    @PostMapping("/smallconcert/detail/{id}/calendar/all")
    @ResponseBody
    public ResponseEntity<Map<String, List<SchedulesDTO>>> calendarAll(@PathVariable("id") int id){

        System.out.println("체크");
        Map<String, List<SchedulesDTO>> response = new HashMap<>();
        List<SchedulesDTO> list = mapService.selectConcertTimeAll(id);
        System.out.println(list);
        response.put("message", list);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/smallconcert/detail/{id}/calendar")
    @ResponseBody
    public ResponseEntity<Map<String, SchedulesDTO>> calendarJson(@PathVariable("id") int id, @RequestBody Map<String, String> data){
        String selectedDate = data.get("selectedDate");
        System.out.println(selectedDate);

        Map<String, SchedulesDTO> response = new HashMap<>();
        response.put("message", mapService.selectConcertTime(id,selectedDate));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/smallconcert/detail/{id}/calendar/{day}")
    public String seatPage(){
        return "html/pay/pay2";
    }

    @GetMapping("/smallconcert/detail/{id}/calendar/{day}/json")
    @ResponseBody
    public Map<String, List<String>> seat(@PathVariable("id")int id,@PathVariable("day")String day){

        System.out.println(mapService.selectBooked(id,day));

        Map<String, List<String>> map = new HashMap<>();

        map.put("temp",mapService.selectBookedTemp(id,day));
        map.put("booked",mapService.selectBooked(id,day));

        return map;
    }
    //seated.js에서 좌석정보 넘겨줌
    @PostMapping ("/smallconcert/detail/{id}/calendar/{day}/pay")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> pay(@PathVariable("id")int id,@PathVariable("day")String day,@RequestBody Map<String, List<String>> data){
        List<String> selectedSeats = data.get("selectedSeats");
        int length = selectedSeats.size();

        Map<String, Object> response = new HashMap<>();
        response.put("seat", selectedSeats);
        response.put("count", length);
        response.put("concert", mapService.selectConcert(id));
        response.put("schedule", mapService.selectConcertTime(id,day));

        dto.setMyData(response);

        int result;
        try {
            mapService.insertSeatTemp(id, day, selectedSeats);
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        response.put("result", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/smallconcert/detail/{id}/calendar/{day}/pay")
    public String payPage(){


        return "html/pay/pay3";
    }

    @GetMapping("/smallconcert/detail/{id}/calendar/{day}/pay/json")
    @ResponseBody
    public DataDTO payPageData(){


        return dto;
    }

    @GetMapping("/busking/detail/{id}/donation")
    public String donation(){
        return "html/pay/buskingDonation";
    }

    @GetMapping("/busking/detail/{id}/donation/json")
    @ResponseBody
    public BuskingDTO donationData(@PathVariable("id") int id){

        BuskingDTO dto = mapService.selectBusking(id);

        return dto;
    }

    //small_cocnert 결제
    @GetMapping("/pay/kakao")
    @ResponseBody
    public String kakaoPay(){
        try {
            return mapService.payService();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/kakaopay/success")
    public String success(){
        return "html/pay/concertPaySuccess";
    }

    @PostMapping("/kakaopay/success")
    @ResponseBody
    public ResponseEntity<Map<String, SmallConcertDTO>> saveConcert(@RequestBody Map<String, String> data,HttpServletRequest request) {

        String conDate = data.get("conDate");

        String conIdStr = data.get("conId");
        int conId = Integer.parseInt(conIdStr);

        String conSeatStr = data.get("conSeat");
        String[] conSeatArr = conSeatStr.split(",");
        List<String> seletedSeats = Arrays.asList(conSeatArr);
        int memberNum = conSeatArr.length;

        String conPriceStr = data.get("conPrice");
        int conPrice = Integer.parseInt(conPriceStr);

        String token = jwtUtils.getAccessToken(request);
        String userId = jwtUtils.getId(token);

        //booked 테이블에 저장
        mapService.insertSeat(conId, conDate, seletedSeats);

        //스케줄 아이디 가져오기
        SchedulesDTO schedulesDTO = mapService.selectConcertTime(conId, conDate);
        int sId = schedulesDTO.getId();

        //reservation 테이블에 저장
        mapService.saveReservation(sId, userId, conDate, memberNum, conPrice);

        //reservation id 가져오기
        ReservationDTO reservationDTO = mapService.findReservId(sId, userId);
        mapService.usedReserv(sId, userId);
        int rId = reservationDTO.getId();

        //ticket 테이블에 저장
        mapService.saveTicket(rId, conSeatStr);

        System.out.println("완료");

        //콘서트 이름 보내주기
        Map<String, SmallConcertDTO> response = new HashMap<>();
        response.put("message", mapService.selectConcert(conId));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/kakaopay/fail")
    public String fail(){

        return "html/pay/concertPayFail";
    }

    @PostMapping("/kakaopay/fail")
    @ResponseBody
    public ResponseEntity<Map<String, SmallConcertDTO>> failJson(@RequestBody Map<String, String> data){
        String conIdStr = data.get("conId");
        int conId = Integer.parseInt(conIdStr);

        Map<String, SmallConcertDTO> response = new HashMap<>();
        response.put("message", mapService.selectConcert(conId));

        return ResponseEntity.ok().body(response);
    }

    //도네이션 결제
    @GetMapping("/pay/kakao/donation")
    @ResponseBody
    public String kakaoDonation(){
        try {
            return mapService.payDonation();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/kakaopay/success/donation")
    public String donationSuccess(){
        return "html/pay/paySuccess";
    }

    @PostMapping("/kakaopay/success/donation")
    @ResponseBody
    public ResponseEntity<Map<String, BuskingDTO>> saveDonation(@RequestBody Map<String, String> data,HttpServletRequest request) {

        System.out.println("시작");

        String priceStr = data.get("price");
        int price = Integer.parseInt(priceStr);

        String idStr = data.get("id");
        int id = Integer.parseInt(idStr);

        String token = jwtUtils.getAccessToken(request);

        String userId = jwtUtils.getId(token);

        System.out.println("userid: " + userId);

        //성공 시 artist_donation 테이블에 저장
        mapService.saveDonation(price, id);
        mapService.saveDonationNum(price, id, userId);

        //성공 페이지 출력 내용
        Map<String, BuskingDTO> response = new HashMap<>();
        response.put("message", mapService.selectBusking(id));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/kakaopay/fail/donation")
    public String donationFail(){

        return "html/pay/payFail";
    }

    @PostMapping("/kakaopay/fail/donation")
    @ResponseBody
    public ResponseEntity<Map<String, BuskingDTO>> donationFailData(@RequestBody Map<String, String> data,HttpServletRequest request){

        String idStr = data.get("id");
        int id = Integer.parseInt(idStr);

        //실패 페이지 출력 내용
        Map<String, BuskingDTO> response = new HashMap<>();
        response.put("message", mapService.selectBusking(id));

        return ResponseEntity.ok().body(response);
    }



}


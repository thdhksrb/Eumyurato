package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.DataDTO;
import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private DataDTO dto;

    @GetMapping("/map")
    public String showMap() {
        return "html/map/map";
    }


    @GetMapping("/table")
    public String table() {
        return "html/pay/pay2";
    }


    @GetMapping("/smallconcert")
    public String smallConcert(){

        return "html/map/smallConcertMap";
    }

    @GetMapping("/smallconcert/json")
    @ResponseBody
    public List<SmallConcertDTO> smallConcertJson() {

        List<SmallConcertDTO> list = mapService.viewSmallConcert();

        return list;
    }

    @GetMapping("/smallconcert/detail/{id}/json")
    @ResponseBody
    public SmallConcertDTO detailJson(@PathVariable("id") int id) {
        SmallConcertDTO dto = mapService.selectConcert(id);

        return dto;
    }

    @GetMapping("/smallconcert/detail/{id}")
    public String detail() {


        return "html/detail/detail";
    }

    @GetMapping("/smallconcert/detail/{id}/calendar")
    public String calendarPage(){
        return "html/pay/pay1";
    }

    @PostMapping("/smallconcert/detail/{id}/calendar")
    public String calendar(){
        return "html/pay/pay1";
    }


    @PostMapping("/smallconcert/detail/{id}/calendar/json")
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
    public List<String> seat(@PathVariable("id")int id,@PathVariable("day")String day){

        System.out.println(mapService.selectBooked(id,day));
        return mapService.selectBooked(id,day);
    }

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
            mapService.insertSeat(id, day, selectedSeats);
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

//    @GetMapping("/smallconcert/detail/{id}/calendar/{day}/pay/complete")
//    public String kakaopayPage(){
//
//
//        return "";
//    }


    @GetMapping("/smallconcert/detail/{id}/calendar/{day}/pay/kakao")
    @ResponseBody
    public String kakaoPay(){
        try {
            URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization","KakaoAK 51728ed0dc1cc881ebce676fb8920a0c");
            connection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true);
            String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=초코파이&quantity=1&total_amount=2200&tax_free_amount=0&approval_url=http://localhost8081/kakaopay/success&cancel_url=http://localhost8081/kakaopay/fail&fail_url=http://localhost8081/kakaopay/fail";
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(param);
            dataOutputStream.close();

            int result = connection.getResponseCode();

            InputStream inputStream;
            if(result==200){
                inputStream = connection.getInputStream();
            }else{
                inputStream = connection.getErrorStream();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/kakaopay/success")
    public String success(){
        return "html/pay/paySuccess";
    }

    @GetMapping("/kakaopay/fail")
    public String fail(){
        return "html/pay/payFail";
    }




}


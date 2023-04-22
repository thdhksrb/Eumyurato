package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.IDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Autowired
    private IDAO dao;

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

        List<SmallConcertDTO> list = dao.viewSmallConcert();

        return list;
    }

    @GetMapping("/smallconcert/detail/{id}/json")
    @ResponseBody
    public SmallConcertDTO detailJson(@PathVariable("id") int id) {
        SmallConcertDTO dto = dao.selectConcert(id);

        return dto;
    }

    @GetMapping("/smallconcert/detail/{id}")
    public String detail() {


        return "html/detail/detail";
    }

    @GetMapping("/smallconcert/detail/{id}/calender")
    public String calenderPage(){
        return "html/pay/pay1";
    }

    @PostMapping("/smallconcert/detail/{id}/calender")
    public String calender(){
        return "html/pay/pay1";
    }


    @PostMapping("/smallconcert/detail/{id}/calender/json")
    @ResponseBody
    public ResponseEntity<Map<String, SchedulesDTO>> calenderJson(@PathVariable("id") int id, @RequestBody Map<String, String> data){
        String selectedDate = data.get("selectedDate");
        System.out.println(selectedDate);

        Map<String, SchedulesDTO> response = new HashMap<>();
        response.put("message", dao.selectConcertTime(id,selectedDate));
        return ResponseEntity.ok().body(response);
    }



}


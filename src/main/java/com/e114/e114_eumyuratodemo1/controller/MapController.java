package com.e114.e114_eumyuratodemo1.controller;

import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.IDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MapController {

    @Autowired
    private IDAO dao;

    @GetMapping("/map")
    public String showMap() {
        return "html/map/map";
    }

    @GetMapping("/calender")
    public String calender() {
        return "html/pay/pay1";
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

}

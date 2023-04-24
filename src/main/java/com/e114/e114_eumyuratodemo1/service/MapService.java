package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.IDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Service
public class MapService {

    @Autowired
    private IDAO dao;


    public List<SmallConcertDTO> viewSmallConcert(){
        return dao.viewSmallConcert();
    };

    public SmallConcertDTO selectConcert(int id){
        return dao.selectConcert(id);
    };
    public SchedulesDTO selectConcertTime(int id, String conDate){
        return dao.selectConcertTime(id,conDate);
    };
    public List<String> selectBooked(int conId,String conDate){
        return dao.selectBooked(conId,conDate);
    };
    public int insertSeat(int conId,String conDate,List<String> seat){
        Map<String,Object> map = new HashMap<>();
        map.put("conId",conId);
        map.put("conDate",conDate);
        map.put("seat",seat);
        return dao.insertSeat(map);
    };


}

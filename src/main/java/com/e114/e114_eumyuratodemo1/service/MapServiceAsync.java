package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.jdbc.IDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@EnableAsync
public class MapServiceAsync {


    @Autowired
    private IDAO dao;

    @Async
    public void deleteSeatTemp(Map<String, Object> map) throws InterruptedException {
        Thread.sleep(600000); //10분
        int result = dao.deleteSeatTemp(map);
        System.out.println("result: "+ result);
    }
}

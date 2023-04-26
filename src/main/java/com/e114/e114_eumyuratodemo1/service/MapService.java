package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.LocalFestivalDTO;
import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.IDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public List<BuskingDTO> viewBusking(){
        return dao.viewBusking();
    };

    public List<LocalFestivalDTO> viewLocalFestival(){
        return dao.viewLocalFestival();
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
    public LocalFestivalDTO selectLocal(int id){
        return dao.selectLocal(id);
    }
    public BuskingDTO selectBusking(int id){
        return dao.selectBusking(id);
    }


    public int insertSeat(int conId, String conDate, List<String> seat) {
        Map<String, Object> map = new HashMap<>();
        map.put("conId", conId);
        map.put("conDate", conDate);
        map.put("seat", seat);
        return dao.insertSeat(map);
    }

    public void rollBackInsertSeat(int schedulesId,List<String> seat){
        Map<String,Object> map = new HashMap<>();
        map.put("schedulesId",schedulesId);
        map.put("seat",seat);
        dao.deleteSeat(map);

    }

    public String payService() throws IOException, MalformedURLException {
        URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization","KakaoAK 51728ed0dc1cc881ebce676fb8920a0c");
        connection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);
        String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=초코파이&quantity=1&total_amount=2200&tax_free_amount=0&approval_url=http://localhost:8081/kakaopay/success&cancel_url=http://localhost:8081/kakaopay/fail&fail_url=http://localhost:8081/kakaopay/fail";
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
    }

    public String donationService() throws IOException, MalformedURLException {
        URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization","KakaoAK 51728ed0dc1cc881ebce676fb8920a0c");
        connection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);
        String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=초코파이&quantity=1&total_amount=2200&tax_free_amount=0&approval_url=http://localhost:8081/kakaopay/success/donation&cancel_url=http://localhost:8081/kakaopay/fail/donation&fail_url=http://localhost:8081/kakaopay/fail/donation";
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
    }



}

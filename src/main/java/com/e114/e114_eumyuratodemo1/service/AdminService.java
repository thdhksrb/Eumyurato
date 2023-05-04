package com.e114.e114_eumyuratodemo1.service;


import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.LocalFestivalDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    AdminMemberDAO dao;

    public List<BuskingDTO> viewAllBusking() {
        return dao.getBuskings();
    }

    ;

    public List<SmallConcertDTO> viewAllSmallConcert() {
        return dao.getSmallConcerts();
    }

    ;

    public List<LocalFestivalDTO> viewAllLocalFestival() {
        return dao.getLocalFestivals();
    }

    ;

    public void deleteEvent(String category, int id) {
        String query = null;
        switch (category) {
            case "busking":
                dao.deleteBusking(id);
                break;
            case "smallconcert":
                dao.deleteSmallConcert(id);
                break;
            case "localfestival":
                dao.deleteLocalFestival(id);
                break;
            default:
                // 지정되지 않은 카테고리에 대한 처리
                break;
        }
    }

    public void registerSmallConcert(SmallConcertDTO smallConcertDTO) {
        dao.registerSmallConcert(smallConcertDTO);
    }

    public List<BuskingDTO> searchBuskings(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchBuskings(params);
    }

    public List<SmallConcertDTO> searchSmallConcerts(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchSmallConcerts(params);
    }

    public List<LocalFestivalDTO> searchLocalFestivals(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchLocalFestivals(params);
    }

}


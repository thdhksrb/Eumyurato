package com.e114.e114_eumyuratodemo1.service;


import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.LocalFestivalDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AdminService {

    private static final String UPLOAD_DIR = "C:/Users/Admin/Desktop/images";

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


}


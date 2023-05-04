package com.e114.e114_eumyuratodemo1.service;


import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Service
public class AdminService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Autowired
    AdminMemberDAO dao;

    public List<CommonMemberDTO> viewAllCommons(){
        return dao.getCommonMembers();
    };

    public List<ArtistMemberDTO> viewAllArtists(){
        return dao.getArtistMembers();
    };

    public List<EnterpriseMemberDTO> viewAllEnters(){
        return dao.getEntMembers();
    };

    public List<CommonMemberDTO> searchCommons(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchCommonMembers(params);
    };

    public List<ArtistMemberDTO> searchArtists(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchArtistMembers(params);
    };

    public List<EnterpriseMemberDTO> searchEnters(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchEntMembers(params);
    };

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

    public void saveConcertWithoutImage(SmallConcertDTO smallConcertDTO){
        dao.saveConcertWithoutImage(smallConcertDTO);
    }

    public void saveConcert(SmallConcertDTO smallConcertDTO, MultipartFile imgFile) throws IOException {
        String originalFileName = imgFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "_" + originalFileName;
        File imageFile = new File(uploadPath, fileName);
        imgFile.transferTo(imageFile);
        smallConcertDTO.setImage(imageFile.getAbsolutePath());
        System.out.println("service DTO : " + smallConcertDTO);
        System.out.println(smallConcertDTO.getImage());
        dao.saveConcert(smallConcertDTO);
    }



}


package com.e114.e114_eumyuratodemo1.service;


import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.AdminMemberDAO;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
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
import java.util.*;

@Service
public class AdminService {

    @Autowired
    AdminMemberDAO dao;

    @Autowired
    private Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public List<CommonMemberDTO> viewAllCommons() {
        return dao.getCommonMembers();
    }

    ;

    public List<ArtistMemberDTO> viewAllArtists() {
        return dao.getArtistMembers();
    }

    ;

    public List<EnterpriseMemberDTO> viewAllEnters() {
        return dao.getEntMembers();
    }

    ;

    public List<CommonMemberDTO> searchCommons(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchCommonMembers(params);
    }

    ;

    public List<ArtistMemberDTO> searchArtists(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchArtistMembers(params);
    }

    ;

    public List<EnterpriseMemberDTO> searchEnters(String column, String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return dao.searchEntMembers(params);
    }

    ;

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

    //일반 회원
    public List<Map<String, Object>> commonGenderCount() {
        return dao.getCommonGender();
    }

    public List<Map<String, Object>> commonGenreCount() {
        return dao.getCommonGenre();
    }

    public List<Map<String, Object>> commonRoadCount() {
        return dao.getCommonRoad();
    }

    //아티스트 회원
    public List<Map<String, Object>> artistGenderCount() {
        return dao.getArtistGender();
    }

    public List<Map<String, Object>> artistGenreCount() {
        return dao.getArtistGenre();
    }

    public List<Map<String, Object>> artistPointTop() {
        return dao.getArtistPoint();
    }

    public List<Map<String, Object>> artistPointAvg() {
        return dao.getArtistPointAvg();
    }

    public List<Map<String, Object>> artistBuskingIng() {
        return dao.getArtistBuskingIng();
    }

    public List<Map<String, Object>> artistBuskingAll() {
        return dao.getArtistBuskingAll();
    }

    //기업 회원
    public List<Map<String, Object>> enterConcertIng() {
        return dao.getEnterConcertIng();
    }

    public List<Map<String, Object>> enterConcertAll() {
        return dao.getEnterConcertAll();
    }

    public List<ReservationDTO> viewAllReservations() {

        return dao.getReservationList();
    }

    public List<ReservationDTO> searchReservations(String column, String keyword) {

        return dao.searchReservations(column, keyword);
    }

    public int deleteReservation(int id) {
        return dao.deleteReservation(id);
    }

    public int deleteTicket(int rid) {
        return dao.deleteTicket(rid);
    }

    public void deleteBusking(int id) {
        dao.deleteBusking(id);
    }

    public void deleteDonation(int buskId) {
        dao.deleteDonation(buskId);
    }

    public void deleteLocalFestival(int id) {
        dao.deleteLocalFestival(id);
    }

    public void deleteSmallConcert(int conId) {

        List<String> sId = dao.getScheduleId(conId);

        List<String> rId = dao.getReservationId(sId);

        if (rId.toArray().length != 0) {
            dao.deleteTickets(rId);

            dao.deleteReservations(sId);

            dao.deleteSchedules(conId);

            dao.deleteSmallConcert(conId);

        } else {
            dao.deleteSchedules(conId);

            dao.deleteSmallConcert(conId);
        }

    }

}


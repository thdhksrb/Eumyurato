package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.*;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberDAO {
    List<ArtistMemberDTO> getArtistMembers();   // 아시트스 회원 목록 불러오기
    List<CommonMemberDTO> getCommonMembers();   // 일반 회원 목록 불러오기
    List<EnterpriseMemberDTO> getEntMembers();  // 기업 회원 목록 불러오기
    List<ArtistMemberDTO> searchArtistMembers(Map<String, String> params);  // 아티스트 회원 검색
    List<CommonMemberDTO> searchCommonMembers(Map<String, String> params);  // 일반 회원 검색
    List<EnterpriseMemberDTO> searchEntMembers(Map<String, String> params); // 기업 회원 검색

    String logout(HttpServletRequest request);  // 로그아웃 기능 설정

    //List<ReservationDTO> getReservationList();  // 예약 내역 목록 불러오기
    //List<ReservationDTO> searchReservations(Map<String, String> params  );  // 예약 내역 검색

    List<ReservationDTO> getReservationList();
    // 일반회원 예약 목록 검색
    List<ReservationDTO> searchReservations(String column, String keyword);

    EnterpriseMemberDTO getAdminInfoById(String adminId);   // 내 계정 정보 불러오기

    List<BuskingDTO> getBuskings();     // 버스킹 목록 불러오기
    List<SmallConcertDTO> getSmallConcerts();   // 소규모 공연 불러오기
    List<LocalFestivalDTO> getLocalFestivals(); // 지역축제 불러오기

    void deleteBusking(int id);
    void deleteSmallConcert(int id);
    void deleteLocalFestival(int id);

    List<BuskingDTO> searchBuskings(Map<String, String> params);
    List<SmallConcertDTO> searchSmallConcerts(Map<String, String> params);
    List<LocalFestivalDTO> searchLocalFestivals(Map<String, String> params);
    void saveConcertWithoutImage(SmallConcertDTO smallConcertDTO);
    void saveConcert(SmallConcertDTO smallConcertDTO);

    //일반 회원
    List<Map<String, Object>> getCommonGender();
    List<Map<String, Object>>  getCommonGenre();
    List<Map<String, Object>> getCommonRoad();

    //아티스트 회원
    List<Map<String, Object>> getArtistGender();
    List<Map<String, Object>> getArtistGenre();
    List<Map<String, Object>> getArtistPoint();
    List<Map<String, Object>> getArtistPointAvg();
    List<Map<String, Object>> getArtistBuskingIng();
    List<Map<String, Object>> getArtistBuskingAll();

    //기업 회원
    List<Map<String, Object>> getEnterConcertIng();
    List<Map<String, Object>> getEnterConcertAll();
}
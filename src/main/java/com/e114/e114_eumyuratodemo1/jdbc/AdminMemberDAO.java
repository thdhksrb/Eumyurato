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

    List<ReservationDTO> getReservationList();  // 예약 내역 목록 불러오기
    List<ReservationDTO> searchReservations(Map<String, String> params  );  // 예약 내역 검색

    EnterpriseMemberDTO getAdminInfoById(String adminId);   // 내 계정 정보 불러오기 기능 (test용)

    List<BuskingDTO> getBuskings();     // 버스킹 목록 불러오기
    List<SmallConcertDTO> getSmallConcerts();   // 소규모 공연 불러오기
    List<LocalFestivalDTO> getLocalFestivals(); // 지역축제 불러오기






    public int registerSmallConcert(Map<String, String> map);   // 소규모 공연 등록
}
package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseMemberDAO{
    EnterpriseMemberDTO findById(@Param("id") String id);
    String findPwdById(@Param("id") String id);
    EnterpriseMemberDTO findByPwd(String pwd);
    EnterpriseMemberDTO findByName(String name);
    EnterpriseMemberDTO findNum (String num);
    EnterpriseMemberDTO findByEmail(String email);
    EnterpriseMemberDTO findByPhone(String phone);
    EnterpriseMemberDTO findByAdminNum(String adminNum);
    EnterpriseMemberDTO findIByImage(String image);

    //회원 가입
    int insert(EnterpriseMemberDTO enterpriseMemberDTO);

    EnterpriseMemberDTO useById(String id); // 아이디 중복 확인


    //아이디 찾기
    List<String> findUserIdsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // 비밀번호 변경
    void updatePassword(@Param("id") String id, @Param("pwd") String password);

    List<CommonMemberDTO> getCommonMembers();   // 일반 회원 목록 불러오기
    List<ArtistMemberDTO> getArtistMembers();   // 아시트스 회원 목록 불러오기
    List<EnterpriseMemberDTO> getEntMembers();  // 기업 회원 목록 불러오기

    List<CommonMemberDTO> searchCommonMembers(Map<String, String> params);  // 일반 회원 검색
    List<ArtistMemberDTO> searchArtistMembers(Map<String, String> params);  // 아티스트 회원 검색
    List<EnterpriseMemberDTO> searchEntMembers(Map<String, String> params); // 기업 회원 검색

    //일반 회원
    List<Map<String, Object>> getCommonGender();    // 일반회원 성별
    List<Map<String, Object>>  getCommonGenre();    // 일반회원 선호 장르

    //아티스트 회원
    List<Map<String, Object>> getArtistGender();    // 아티스트 회원 성별
    List<Map<String, Object>> getArtistGenre();     // 아티스트 회원 장르

    //기업 회원
    List<Map<String, Object>> getEnterConcertIng(); // 기업회원 진행 중인 소규모 공연 수
    List<Map<String, Object>> getEnterConcertAll(); // 기업회원 모든 소규모 공연 수

    EnterpriseMemberDTO getEntInfoById(String entId);   // 기업회원 정보 가져오기

    // 기업회원 소규모공연 목록 가져오기
    List<SmallConcertDTO> getEntSmallConcerts(String enterId);     // 소규모 공연 목록 불러오기
    List<SmallConcertDTO> searchEntSmallConcerts(String enterId, String column, String keyword); // 소규모 공연 검색

    List<ReservationDTO> searchReservations(Map<String, String> params  );

    List<SmallConcertDTO> getSmallConcertId(String enterId);
    List<SchedulesDTO> getScheduleId(int conId);
    List<ReservationDTO> getReservationId(int sid);
}
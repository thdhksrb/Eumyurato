package com.e114.e114_eumyuratodemo1.dao;

import com.e114.e114_eumyuratodemo1.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseMemberDAO{
    EnterpriseMemberDTO findById(@Param("id") String id);
    EnterpriseMemberDTO findByEmail(String email);

    //회원 가입
    int insert(EnterpriseMemberDTO enterpriseMemberDTO);

    // 아이디 중복 확인
    EnterpriseMemberDTO useById(String id);
    boolean isIdDuplicated(String id);

    // 닉네임 중복 확인
    EnterpriseMemberDTO useByNid(String nid);

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

    EnterpriseMemberDTO getEntInfoById(String entId);   // 기업회원 정보 가져오기

    // 기업회원 소규모공연 목록 가져오기
    List<SmallConcertDTO> getEntSmallConcerts(String enterId);     // 소규모 공연 목록 불러오기
    List<SmallConcertDTO> searchEntSmallConcerts(String enterId, String column, String keyword); // 소규모 공연 검색

    List<ReservationDTO> searchReservations(Map<String, String> params  );

    List<ReservationDTO> searchReservationsByEnterId(String enterId, String column, String keyword);
    List<ReservationDTO> getReservationsByEnterId(String enterId);
    //정보 수정
    void modifyEnterWithoutImage(EnterpriseMemberDTO enterpriseMemberDTO);
    void enterModify(EnterpriseMemberDTO enterpriseMemberDTO);

    SmallConcertDTO getSmallConcertByAll(String name,int price,String startDate,String lastDate);

    void saveSchedules(int conId,String conDate);   // 스케쥴 저장

    void saveConcertWithoutImage(SmallConcertDTO smallConcertDTO); // 소규모 공연 등록(이미지 x)
    void saveConcert(SmallConcertDTO smallConcertDTO);              //소규모 공연 등록(이미지 o)

    int deleteSmallConcert(int id);             // 소규모 공연 삭제

    List<String> getScheduleId(int conId);
    List<String> getReservationId(List<String> sId);

    int deleteReservations(List<String> sId);
    int deleteTickets(List<String> rId);
    int deleteSchedules(int id);

    List<InfoDTO> getInfo();
}
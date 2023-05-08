package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    EnterpriseMemberDTO getEntInfoById(String entId);   // 기업회원 정보 가져오기

    // 기업회원 소규모공연 목록 가져오기
    List<SmallConcertDTO> getEntSmallConcerts(String smallConcertId);     // 소규모 공연 목록 불러오기
    List<SmallConcertDTO> searchEntSmallConcerts(String smallConcertId, String column, String keyword); // 소규모 공연 검색
}
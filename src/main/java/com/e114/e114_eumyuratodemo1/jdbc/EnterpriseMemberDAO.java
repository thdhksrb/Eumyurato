package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
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

    // 비밀번호 변경
    void updatePassword(@Param("id") String id, @Param("pwd") String password);
}
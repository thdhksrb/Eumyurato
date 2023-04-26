package com.e114.e114_eumyuratodemo1.mapper;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<CommonMemberDTO> getUserList(); // User 테이블 가져오기
    void insertUser(CommonMemberDTO commonMemberDTO); // 회원 가입
    CommonMemberDTO getUserById(String id); // 회원 정보 가져오기
    CommonMemberDTO getUserByPwd(String pwd);
    void updateUser(CommonMemberDTO commonMemberDTO); // 회원 정보 수정
    void deleteUser(String id); // 회원 탈퇴
}
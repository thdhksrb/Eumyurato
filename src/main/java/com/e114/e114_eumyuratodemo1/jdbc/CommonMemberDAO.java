package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMemberDAO {
 CommonMemberDTO findById(String id);
 CommonMemberDTO findByPwd(String pwd);
 CommonMemberDTO findByName(String name);
 CommonMemberDTO findByNid(String nid);
 CommonMemberDTO findBySex(String sex);
 CommonMemberDTO findByBirth(String birth);
 CommonMemberDTO findByEmail(String email);
 CommonMemberDTO findByPhone(String phone);
 CommonMemberDTO findByRoad(String road);
 CommonMemberDTO findByGenre(String genre);
 CommonMemberDTO findByFavorite(String favorite);
 CommonMemberDTO findByImage(String image);
 CommonMemberDTO findByAdminNum(int adminNum);

 // 회원 가입
 int insert(CommonMemberDTO commonMemberDTO);

 // 아이디 중복 확인
 CommonMemberDTO useById(String id);

 // 비번 중복 확인
 CommonMemberDTO useByNid(String nid);

 // 아이디 찾기
 List<String> findUserIdsByNameAndEmail(String name, String email);

 // 비밀번호 변경
 void updatePassword(@Param("id") String id, @Param("pwd") String password);
}

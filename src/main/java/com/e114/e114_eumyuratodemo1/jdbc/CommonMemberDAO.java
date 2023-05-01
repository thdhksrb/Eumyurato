package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface CommonMemberDAO {
 CommonMemberDTO findById(@Param("id") String id);
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

 //회원 가입
 int insert(CommonMemberDTO commonMemberDTO);

 CommonMemberDTO useById(String id); // 아이디 중복 확인
 CommonMemberDTO useByNid(String nid); //비번 중복 확인
 // 회원 정보 수정, 삭제 등의 메소드 추가 가능
}
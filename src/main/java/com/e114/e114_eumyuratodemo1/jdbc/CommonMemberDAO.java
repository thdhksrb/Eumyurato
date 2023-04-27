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
 CommonMemberDTO findNid(String nid);
 CommonMemberDTO findSex(String sex);
 CommonMemberDTO findBirth(String birth);
 CommonMemberDTO findByEmail(String email);
 CommonMemberDTO findByPhone(String phone);
 CommonMemberDTO findRoad(String road);
 CommonMemberDTO findGenre(String genre);
 CommonMemberDTO findFavorite(String favorite);
 CommonMemberDTO findByImage(String image);
 CommonMemberDTO findByAdminNum(int adminNum);
}
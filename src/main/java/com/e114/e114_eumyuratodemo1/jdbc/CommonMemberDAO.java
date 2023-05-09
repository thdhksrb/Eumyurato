package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
 boolean isIdDuplicated(String id);

 // 닉네임 중복 확인
 CommonMemberDTO useByNid(String nid);

 // 비밀번호 변경
 void updatePassword(@Param("id") String id, @Param("pwd") String password);

 //아이디 찾기
 List<String> findUserIdsByNameAndEmail(@Param("name") String name, @Param("email") String email);

 CommonMemberDTO getCommonInfoById(String commonId);

 // 일반회원 예약 목록 불러오기
 List<ReservationDTO> getCommonReservations(String cId);
 // 일반회원 예약 목록 검색
 List<ReservationDTO> searchCommonReservations(String cId, String column, String keyword);

 // 일반회원 예약 목록 삭제
 int deleteCommonReservation(int id);

 //회원정보 수정
 void modifyCommonWithoutImage(CommonMemberDTO commonMemberDTO);
 void commonModify(CommonMemberDTO commonMemberDTO);
 //닉네임 반환
 int commonNid(String nid);
}

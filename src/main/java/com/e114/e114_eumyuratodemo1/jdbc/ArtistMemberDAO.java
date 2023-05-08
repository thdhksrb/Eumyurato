package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArtistMemberDAO{
    ArtistMemberDTO  findById(@Param("id") String id);
    String findPwdById(@Param("id") String id);
    ArtistMemberDTO findByPwd(String pwd);
    ArtistMemberDTO findByName(String name);
    ArtistMemberDTO findNid(String nid);
    ArtistMemberDTO findSex(String sex);
    ArtistMemberDTO findBrith(String brith);
    ArtistMemberDTO findByEmail(String email);
    ArtistMemberDTO findByPhone(String phone);
    ArtistMemberDTO findGenre(String genre);
    ArtistMemberDTO findByAdminNum(String adminNum);
    ArtistMemberDTO findRegistCon(String registCon);
    ArtistMemberDTO findPoint(String point);
    ArtistMemberDTO findIByImage(String image);

    //회원 가입
    int insert(ArtistMemberDTO artistMemberDTO);

    ArtistMemberDTO useById(String id); // 아이디 중복 확인
    ArtistMemberDTO useByNid(String nid); // 중복 확인

    //아이디 찾기
    List<String> findUserIdsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // 비밀번호 변경
    void updatePassword(@Param("id") String id, @Param("pwd") String password);


    // 아티스트 정보 가져오기
    ArtistMemberDTO getArtistInfoById(String artistId);

    // 아티스트 버스킹 가져오기
    List<BuskingDTO> getArtistBuskings(String artId);     // 버스킹 목록 불러오기
    List<BuskingDTO> searchArtistBuskings(String artId, String column, String keyword);

    int deleteArtistBusking(int id);

    List<ArtistMemberDTO> getArtistMembers();   // 아시트스 회원 목록 불러오기
    List<CommonMemberDTO> getCommonMembers();   // 일반 회원 목록 불러오기

    List<ArtistMemberDTO> searchArtistMembers(Map<String, String> params);  // 아티스트 회원 검색
    List<CommonMemberDTO> searchCommonMembers(Map<String, String> params);  // 일반 회원 검색

    // 일반회원 통계
    List<Map<String, Object>> getCommonGender();
    List<Map<String, Object>>  getCommonGenre();

    // 아티스트 회원 통계
    List<Map<String, Object>> getArtistGender();
    List<Map<String, Object>> getArtistGenre();
    List<Map<String, Object>> getArtistPoint();
    List<Map<String, Object>> getArtistPointAvg();
    List<Map<String, Object>> getArtistBuskingIng();
    List<Map<String, Object>> getArtistBuskingAll();

    //아티스트 버스킹 저장
    void saveBuskingWithoutImage(BuskingDTO buskingDTO);
    void saveBusking(BuskingDTO buskingDTO);

}
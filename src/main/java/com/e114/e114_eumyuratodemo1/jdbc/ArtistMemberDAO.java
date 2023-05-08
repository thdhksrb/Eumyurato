package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
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

    // 아티스트 정보 가져오기
    ArtistMemberDTO getArtistInfoById(String artistId);

    // 아티스트 버스킹 가져오기
    List<BuskingDTO> getArtistBuskings(String artId);     // 버스킹 목록 불러오기
    List<BuskingDTO> searchArtistBuskings(String artId, String column, String keyword);

    int deleteArtistBusking(int id);
}
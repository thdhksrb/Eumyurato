package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArtistMemberDAO{
    ArtistMemberDTO findById(String id);
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

}

package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArtistMemberDAO extends MemberDAO {
    ArtistMemberDTO findNid(String nid);
    ArtistMemberDTO findSex(String sex);
    ArtistMemberDTO findBrith(String brith);
    ArtistMemberDTO findGenre(String genre);
    ArtistMemberDTO findRegistCon(String registCon);
    ArtistMemberDTO findPoint(String point);

}

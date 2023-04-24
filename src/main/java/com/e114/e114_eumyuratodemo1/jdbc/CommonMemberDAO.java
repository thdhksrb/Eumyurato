package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMemberDAO extends MemberDAO {
    CommonMemberDTO findNid(String nid);
    CommonMemberDTO findSex(String sex);
    CommonMemberDTO findBirth(String birth);
    CommonMemberDTO findRoad(String road);
    CommonMemberDTO findGenre(String genre);
    CommonMemberDTO findFavorite(String favorite);
}


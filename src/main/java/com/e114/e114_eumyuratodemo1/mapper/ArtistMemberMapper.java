package com.e114.e114_eumyuratodemo1.mapper;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArtistMemberMapper {
    //아티스트 랭킹
    @Select("SELECT id, name, genre, point, image FROM artist_member ORDER BY point DESC LIMIT 5")
    List<ArtistMemberDTO> selectTop5Artists();
}



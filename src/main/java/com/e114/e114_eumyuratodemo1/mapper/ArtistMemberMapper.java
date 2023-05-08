package com.e114.e114_eumyuratodemo1.mapper;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArtistMemberMapper {
    @Select("SELECT id, name, genre, point FROM artist_member ORDER BY point DESC LIMIT 5")
    List<ArtistMemberDTO> selectTop5Artists();
}



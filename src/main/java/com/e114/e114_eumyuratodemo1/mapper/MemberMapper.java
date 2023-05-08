package com.e114.e114_eumyuratodemo1.mapper;

import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
// MemberMapper.java
public interface MemberMapper {
    //아이디 찾기
    List<String> findUserIdsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // 공연 랭킹
    @Select("SELECT id, name, viewCount FROM small_concert ORDER BY viewCount DESC LIMIT 5")
    List<SmallConcertDTO> selectTop5Concert();
}

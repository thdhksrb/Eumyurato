package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberDAO {
    List<ArtistMemberDTO> getArtistMembers();
    List<CommonMemberDTO> getCommonMembers();
    List<ArtistMemberDTO> searchArtistMembers(Map<String, String> params);
    List<CommonMemberDTO> searchCommonMembers(Map<String, String> params);
}
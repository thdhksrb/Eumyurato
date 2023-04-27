package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberDAO {
    List<ArtistMemberDTO> getArtistMembers();
    List<CommonMemberDTO> getCommonMembers();
    List<EnterpriseMemberDTO> getEntMembers();
    List<ArtistMemberDTO> searchArtistMembers(Map<String, String> params);
    List<CommonMemberDTO> searchCommonMembers(Map<String, String> params);
    List<EnterpriseMemberDTO> searchEntMembers(Map<String, String> params);
    String logout(HttpServletRequest request);
    List<ReservationDTO> getReservationList();
    List<ReservationDTO> searchReservations(Map<String, String> params);

    EnterpriseMemberDTO getAdminInfoById(String adminId);
}
package com.e114.e114_eumyuratodemo1.mapper;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommonMemberMapper {

    @Select("SELECT * FROM common_member WHERE id = #{id}")
    CommonMemberDTO selectCommonMemberById(String id);

    // 회원 정보 수정 처리
    @Update("UPDATE common_member SET "
            + "<if test='nid != null'>nid = #{nid},</if>"
            + "<if test='phone != null'>phone = #{phone},</if>"
            + "<if test='email != null'>email = #{email},</if>"
            + "WHERE id = #{id}")
    int updateCommonMember(CommonMemberDTO commonMemberDTO);

}

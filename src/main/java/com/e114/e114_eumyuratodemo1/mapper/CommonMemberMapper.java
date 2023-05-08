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

    @Update("UPDATE common_member SET nid = #{nid}, phone = #{phone}, email = #{email} WHERE id = #{id}")
    int updateCommonMember(CommonMemberDTO commonMemberDTO);

}

package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnterpriseMemberDAO extends MemberDAO {
    EnterpriseMemberDTO findNum (String num);

    void save (EnterpriseMemberDTO enterpriseMemberDTO);

}

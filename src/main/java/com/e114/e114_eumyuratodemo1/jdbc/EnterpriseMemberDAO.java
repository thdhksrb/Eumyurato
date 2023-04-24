package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnterpriseMemberDAO{
    EnterpriseMemberDTO findById(String id);
    EnterpriseMemberDTO findByPwd(String pwd);
    EnterpriseMemberDTO findByName(String name);
    EnterpriseMemberDTO findNum (String num);
    EnterpriseMemberDTO findByEmail(String email);
    EnterpriseMemberDTO findByPhone(String phone);
    EnterpriseMemberDTO findByAdminNum(String adminNum);
    EnterpriseMemberDTO findIByImage(String image);


}

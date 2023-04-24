package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO{
    MemberDTO findById(String id);
    MemberDTO findByPwd(String pwd);
    MemberDTO findByName(String name);
    MemberDTO findByEmail(String email);
    MemberDTO findByPhone(String phone);
    MemberDTO findByAdminNum(String adminNum);
    MemberDTO findIByImage(String image);

    void save(MemberDTO memberDTO);
}

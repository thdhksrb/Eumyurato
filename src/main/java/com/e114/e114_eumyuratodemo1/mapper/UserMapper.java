package com.e114.e114_eumyuratodemo1.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 기존 메서드들...
    List<String> findRolesByUserId(String userId); // 인자로 받은 사용자 아이디를 통해 해당 사용자의 권한(role) 정보를 조회하여 List<String> 형태로 반환하는 메서드

}
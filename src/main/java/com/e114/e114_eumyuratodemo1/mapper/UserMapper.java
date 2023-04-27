package com.e114.e114_eumyuratodemo1.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 기존 메서드들...
    List<String> findRolesByUserId(String userId);

}
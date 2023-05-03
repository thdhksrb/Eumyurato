package com.e114.e114_eumyuratodemo1.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

// MemberMapper.java
public interface MemberMapper {
    List<String> findUserIdsByNameAndEmail(@Param("name") String name, @Param("email") String email);
}

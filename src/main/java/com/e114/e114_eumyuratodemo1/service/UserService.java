package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CommonMemberDAO commonMemberDAO;

/*    public void register(CommonMemberDTO commonMemberDTO) {
        // 회원가입 로직 구현
        commonMemberDAO.insert(commonMemberDTO);
    }*/

/*    public CommonMemberDTO login(String id, String pwd) {
        // 로그인 로직 구현
        CommonMemberDTO commonMemberDTO = commonMemberDAO.selectByEmail(email);
        if (commonMemberDTO != null && commonMemberDTO.getPassword().equals(pwd)) {
            return commonMemberDTO;
        } else {
            return null;
        }
    }*/
/*
    public void updateProfile(UserDto userDto) {
        // 회원정보 수정 로직 구현
        userDao.update(userDto);
    }*/
}


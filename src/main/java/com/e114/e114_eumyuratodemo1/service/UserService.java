package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CommonMemberDAO commonMemberDAO;

    public CommonMemberDTO login(String id, String pwd) {
        CommonMemberDTO commonMemberDTO = commonMemberDAO.findById(id);
        if (commonMemberDTO != null && commonMemberDTO.getPwd().equals(pwd)) {
            return commonMemberDTO;
        } else {
            return null;
        }
    }
}



/*    public void register(CommonMemberDTO commonMemberDTO) {
        // 회원가입 로직 구현
        commonMemberDAO.insert(commonMemberDTO);
    }*/

/*
    public void updateProfile(UserDto userDto) {
        // 회원정보 수정 로직 구현
        userDao.update(userDto);
    }*/



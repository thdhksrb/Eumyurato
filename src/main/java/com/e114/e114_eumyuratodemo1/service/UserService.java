package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    public ArtistMemberDTO loginArt(String id, String pwd) {
        ArtistMemberDTO artistMemberDTO = artistMemberDAO.findById(id);
        if (artistMemberDTO != null && artistMemberDTO.getPwd().equals(pwd)) {
            return artistMemberDTO;
        } else {
            return null;
        }
    }

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    public EnterpriseMemberDTO loginenter(String id, String pwd) {
        EnterpriseMemberDTO enterpriseMemberDTO = enterpriseMemberDAO.findById(id);
        if (enterpriseMemberDTO != null && enterpriseMemberDTO.getPwd().equals(pwd)) {
            return enterpriseMemberDTO;
        } else {
            return null;
        }
    }

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<CommonMemberDTO> getUserList() {
        return userMapper.getUserList();
    }

    public CommonMemberDTO getPassword (String pwd) {
        return userMapper.getUserByPwd(pwd);
    }

    public CommonMemberDTO getId (String id){
        return userMapper.getUserById(id);
    }

/*    public void signup(CommonMemberDTO commonMemberDTO) { // 회원 가입
        if (!commonMemberDTO.getName().equals("") && !commonMemberDTO.getId().equals("")) {
            // password는 암호화해서 DB에 저장
            userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
            userMapper.insertUser(userVo);
        }
    }

    public void edit(UserVo userVo) { // 회원 정보 수정
        // password는 암호화해서 DB에 저장
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
        userMapper.updateUser(userVo);
    }

    public void withdraw(Long id) { // 회원 탈퇴
        userMapper.deleteUser(id);
    }*/

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}



/*    public void register(CommonMemberDTO commonMemberDTO) {
        // 회원가입 로직 구현
        commonMemberDAO.insert(commonMemberDTO);
    }


    public void updateProfile(UserDto userDto) {
        // 회원정보 수정 로직 구현
        userDao.update(userDto);
    }*/




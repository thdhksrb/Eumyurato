package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

 //로그인 요청 처리, 사용자 아이디에 해당하는 권한 정보 조회를 담당
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    // 로그인 요청 처리, 사용자 아이디와 비밀번호를 받아 DB에서 일치하는 사용자 정보를 찾습니다.
// 찾은 경우 사용자 정보를 반환하고, 일치하는 정보가 없는 경우 null을 반환합니다.
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

    // 아티스트 회원용 로그인 요청 처리, 사용자 아이디와 비밀번호를 받아 DB에서 일치하는 사용자 정보를 찾습니다.
    // 찾은 경우 사용자 정보를 반환하고, 일치하는 정보가 없는 경우 null을 반환합니다.
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

    // 기업 회원용 로그인 요청 처리, 사용자 아이디와 비밀번호를 받아 DB에서 일치하는 사용자 정보를 찾습니다.
    // 찾은 경우 사용자 정보를 반환하고, 일치하는 정보가 없는 경우 null을 반환합니다.
    public EnterpriseMemberDTO loginenter(String id, String pwd) {
        EnterpriseMemberDTO enterpriseMemberDTO = enterpriseMemberDAO.findById(id);
        if (enterpriseMemberDTO != null && enterpriseMemberDTO.getPwd().equals(pwd)) {
            return enterpriseMemberDTO;
        } else {
            return null;
        }
    }

     // 사용자 아이디에 해당하는 권한 정보를 DB에서 조회합니다.
     public List<String> getUserRoles(String userId) {
         return userMapper.findRolesByUserId(userId);
     }

     @Configuration
     public class AppConfig {

         @Bean
         public RestTemplate restTemplate() {
             return new RestTemplate();
         }
     }
 }







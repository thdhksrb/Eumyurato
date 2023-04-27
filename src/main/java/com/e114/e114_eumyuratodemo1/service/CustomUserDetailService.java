package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final CommonMemberDAO commonMemberDAO;

    public CustomUserDetailService(CommonMemberDAO commonMemberDAO) {
        this.commonMemberDAO = commonMemberDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonMemberDTO commonMemberDTO = commonMemberDAO.findByEmail(username);
        if (commonMemberDTO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return new org.springframework.security.core.userdetails.User(
                commonMemberDTO.getEmail(), commonMemberDTO.getPwd(),
                commonMemberDTO.getAuthorities()
        );
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        CommonMemberDTO commonMemberDTO = commonMemberDAO.findById(String.valueOf(id));
        if (commonMemberDTO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return new org.springframework.security.core.userdetails.User(
                commonMemberDTO.getEmail(), commonMemberDTO.getPwd(),
                commonMemberDTO.getAuthorities()
        );
    }
}



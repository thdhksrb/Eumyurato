/*
package com.e114.e114_eumyuratodemo1.config;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonMemberDTO commonMemberDTO = commonMemberDAO.findById(username);
        if (commonMemberDTO == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(commonMemberDTO.getId(), commonMemberDTO.getPwd(),
                new ArrayList<>());
    }
}

*/

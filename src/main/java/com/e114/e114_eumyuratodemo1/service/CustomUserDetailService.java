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

    // 로그인 시, 사용자의 email로 사용자 정보 조회하여 UserDetails 객체를 리턴하는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonMemberDTO commonMemberDTO = commonMemberDAO.findByEmail(username);
        if (commonMemberDTO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        // Spring Security에서 제공하는 User 클래스를 이용하여 UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
                commonMemberDTO.getEmail(), commonMemberDTO.getPwd(),
                commonMemberDTO.getAuthorities()
        );
    }

    // JWT 토큰에서 인증 정보 조회를 위한 메소드
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

/*@Service : 스프링에서 사용하는 서비스 계층에 사용되는 어노테이션입니다.
UserDetailsService : 스프링 시큐리티에서 인증을 위해 사용되는 인터페이스로, loadUserByUsername 메소드를 구현하여 사용자 정보를 가져오게 됩니다.
CommonMemberDAO : 사용자 정보를 가져오기 위해 데이터베이스에 접근하기 위한 DAO(Data Access Object) 클래스입니다.
loadUserByUsername : 사용자의 이메일로 사용자 정보를 조회하여 UserDetails 객체를 생성하여 리턴합니다. 만약 사용자 정보가 존재하지 않으면 UsernameNotFoundException 예외를 발생시킵니다.
loadUserById : JWT 토큰에서 인증 정보를 가져오기 위해 사용자 ID로 사용자 정보를 조회하여 UserDetails 객체를 생성하여 리턴합니다. 만약 사용자 정보가 존재하지 않으면 UsernameNotFoundException 예외를 발생시킵니다.*/


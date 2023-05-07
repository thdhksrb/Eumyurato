package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//로그인 요청 처리, 사용자 아이디에 해당하는 권한 정보 조회를 담당
@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    // 기업 회원용 로그인 요청 처리, 사용자 아이디와 비밀번호를 받아 DB에서 일치하는 사용자 정보를 찾습니다.
    // 찾은 경우 사용자 정보를 반환하고, 일치하는 정보가 없는 경우 null을 반환합니다.
    public EnterpriseMemberDTO login(String id, String pwd) {
        EnterpriseMemberDTO enterpriseMemberDTO = enterpriseMemberDAO.findById(id);
        if (enterpriseMemberDTO != null && enterpriseMemberDTO.getPwd().equals(pwd)) {
            return enterpriseMemberDTO;
        } else {
            return null;
        }
    }

    //기업 회원가입
    public boolean register(String id, String pwd, String name, String num, String email, String phone) {
        // 회원 정보 유효성 검사
        if (id == null || id.isEmpty() || pwd == null || pwd.isEmpty() || name == null || name.isEmpty()
                || num == null || num.isEmpty() || email == null || email.isEmpty() || phone == null || phone.isEmpty()) {
            return false;
        }

        // 이미 사용 중인 아이디인지 검사
        if (enterpriseMemberDAO.useById(id) != null) {
            return false;
        }

        // 아이디 유효성 검사
        if (!id.matches("^[a-zA-Z0-9]{5,20}$")) {
            return false;
        }

        // 비밀번호 유효성 검사
        if (!pwd.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")) {
            return false;
        }

        // 이메일 유효성 검사
        if (!email.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")) {
            return false;
        }

        // 휴대폰 번호 유효성 검사
        if (!phone.matches("^01[016789][1-9]\\d{6,7}$")) {
            return false;
        }

        // CommonMemberDTO 객체 생성 및 정보 설정
        EnterpriseMemberDTO enterpriseMemberDTO = new EnterpriseMemberDTO();
        enterpriseMemberDTO.setId(id);
        enterpriseMemberDTO.setPwd(pwd);
        enterpriseMemberDTO.setName(name);
        enterpriseMemberDTO.setNum(num);
        enterpriseMemberDTO.setEmail(email);
        enterpriseMemberDTO.setPhone(phone);
        enterpriseMemberDTO.setAdminNum(3);  // 아티스트 회원의 경우 adminNum 값을 1로 설정

        // artistMemberDAO를 사용하여 회원 정보 저장
        int result = enterpriseMemberDAO.insert(enterpriseMemberDTO);
        return result == 3;
    }

}







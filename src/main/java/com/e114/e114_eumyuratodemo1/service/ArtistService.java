package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.mapper.ArtistMemberMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


//로그인 요청 처리, 사용자 아이디에 해당하는 권한 정보 조회를 담당
@Service
public class ArtistService {

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private  ArtistMemberDTO artistMemberDTO;

    // 아티스트 회원용 로그인 요청 처리, 사용자 아이디와 비밀번호를 받아 DB에서 일치하는 사용자 정보를 찾습니다.
    // 찾은 경우 사용자 정보를 반환하고, 일치하는 정보가 없는 경우 null을 반환합니다.
    public ArtistMemberDTO login(String id, String pwd) {
        ArtistMemberDTO artistMemberDTO = artistMemberDAO.findById(id);
        if (artistMemberDTO != null && artistMemberDTO.getPwd().equals(pwd)) {
            return artistMemberDTO;
        } else {
            return null;
        }
    }


    //아티스트 회원가입
    public boolean register(String id, String pwd, String name, String nid, String sex, String birth,
                            String email, String phone, String genre) {
        // 회원 정보 유효성 검사
        if (id == null || id.isEmpty() || pwd == null || pwd.isEmpty() || name == null || name.isEmpty()
                || nid == null || nid.isEmpty() || birth == null || birth.isEmpty() || sex == null || sex.isEmpty()
                || email == null || email.isEmpty() || phone == null || phone.isEmpty()) {
            return false;
        }

        // 이미 사용 중인 아이디인지 검사
        if (artistMemberDAO.useById(id) != null) {
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

        // 이미 사용 중인 닉네임인지 검사
        if (artistMemberDAO.useByNid(nid) != null) {
            return false;
        }

        // 생년월일 유효성 검사 (ex. 2000-01-01)
        if (!birth.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
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
        ArtistMemberDTO artistMemberDTO= new ArtistMemberDTO();
        artistMemberDTO.setId(id);
        artistMemberDTO.setPwd(pwd);
        artistMemberDTO.setName(name);
        artistMemberDTO.setNid(nid);
        artistMemberDTO.setBirth(birth);
        artistMemberDTO.setSex(sex); // 이 부분에서 NullPointerException이 발생할 가능성이 있습니다.
        artistMemberDTO.setEmail(email);
        artistMemberDTO.setPhone(phone);
        artistMemberDTO.setGenre(genre);
        artistMemberDTO.setAdminNum(2);  // 아티스트 회원의 경우 adminNum 값을 1로 설정

        // artistMemberDAO를 사용하여 회원 정보 저장
        int result = artistMemberDAO.insert(artistMemberDTO);
        return result == 2;
    }


    //아티스트 랭킹
    private final ArtistMemberMapper artistMemberMapper;

    public ArtistService(ArtistMemberMapper artistMemberMapper) {
        this.artistMemberMapper = artistMemberMapper;
    }

    public List<ArtistMemberDTO> selectTop5Artists() {
        return artistMemberMapper.selectTop5Artists();
    }


    }

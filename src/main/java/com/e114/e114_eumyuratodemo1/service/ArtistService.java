package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//로그인 요청 처리, 사용자 아이디에 해당하는 권한 정보 조회를 담당
@Service
public class ArtistService {

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private  ArtistMemberDTO artistMemberDTO;

    @Autowired
    private JwtUtils jwtUtils;

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
                || nid == null || nid.isEmpty() || birth == null
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

    public List<CommonMemberDTO> viewAllCommons(){
        return artistMemberDAO.getCommonMembers();
    };

    public List<ArtistMemberDTO> viewAllArtists(){
        return artistMemberDAO.getArtistMembers();
    };

    public List<BuskingDTO> viewArtistBusking(String artId) {

        return artistMemberDAO.getArtistBuskings(artId);
    }

    public List<BuskingDTO> searchArtistBusking(String artistId, String column, String keyword) {

        return artistMemberDAO.searchArtistBuskings(artistId, column, keyword);
    }

    public List<CommonMemberDTO> searchCommons(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return artistMemberDAO.searchCommonMembers(params);
    };

    public List<ArtistMemberDTO> searchArtists(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return artistMemberDAO.searchArtistMembers(params);
    };

    public List<Map<String, Object>>  commonGenderCount(){
        return artistMemberDAO.getCommonGender();
    }
    public List<Map<String, Object>>  commonGenreCount(){
        return artistMemberDAO.getCommonGenre();
    }

    public List<Map<String, Object>>  artistGenderCount(){
        return artistMemberDAO.getArtistGender();
    }
    public List<Map<String, Object>>  artistGenreCount(){
        return artistMemberDAO.getArtistGenre();
    }
    public List<Map<String, Object>>  artistPointTop(){
        return artistMemberDAO.getArtistPoint();
    }
    public List<Map<String, Object>>  artistPointAvg(){
        return artistMemberDAO.getArtistPointAvg();
    }
    public List<Map<String, Object>>  artistBuskingIng(){
        return artistMemberDAO.getArtistBuskingIng();
    }
    public List<Map<String, Object>>  artistBuskingAll(){
        return artistMemberDAO.getArtistBuskingAll();
    }



    public int deleteBusking(int id) {
        return artistMemberDAO.deleteArtistBusking(id);
    }

}
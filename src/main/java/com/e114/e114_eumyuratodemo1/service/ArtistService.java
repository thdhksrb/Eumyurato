package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.ArtistMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dao.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jwt.JwtUtils;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


//로그인 요청 처리, 사용자 아이디에 해당하는 권한 정보 조회를 담당
@Service
public class ArtistService {

    @Autowired
    private ArtistMemberDAO artistMemberDAO;


    @Autowired
    private Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;


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
            //return false;
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
        artistMemberDTO.setAdminNum(2);  // 아티스트 회원의 경우 adminNum 값을 2로 설정

        // artistMemberDAO를 사용하여 회원 정보 저장
        int result = artistMemberDAO.insert(artistMemberDTO);
        return result == 1;
    }






    public List<ArtistMemberDTO> selectTop5Artists() {
        return artistMemberDAO.selectTop5Artists();
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

    public int deleteBusking(int id) {
        return artistMemberDAO.deleteArtistBusking(id);
    }

    //버스킹 저장
    public void saveBuskingWithoutImage(BuskingDTO buskingDTO){
        artistMemberDAO.saveBuskingWithoutImage(buskingDTO);
    }

    public void saveBusking(BuskingDTO buskingDTO, MultipartFile imgFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = imgFile.getContentType();

        //이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                imgFile.getInputStream()
        );

        buskingDTO.setImage(uuid);
        artistMemberDAO.saveBusking(buskingDTO);
    }

    //회원정보 수정
    public void modifyArtistWithoutImage(ArtistMemberDTO artistMemberDTO){
        artistMemberDAO.modifyArtistWithoutImage(artistMemberDTO);
    }

    public void artistModify(ArtistMemberDTO artistMemberDTO, MultipartFile imgFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = imgFile.getContentType();

        //이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                imgFile.getInputStream()
        );
        artistMemberDTO.setImage(uuid);

        artistMemberDAO.artistModify(artistMemberDTO);
    }

    //회원 닉네임 중복 체크
    public List<Map<String, Object>> artistNid(String nid){
        return artistMemberDAO.artistNid(nid);
    }
}

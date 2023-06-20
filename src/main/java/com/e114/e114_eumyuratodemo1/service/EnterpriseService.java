package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.dao.EnterpriseMemberDAO;
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
public class EnterpriseService {

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    @Autowired
    private Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;


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
        enterpriseMemberDTO.setAdminNum(3);  // 기업 회원의 경우 adminNum 값을 3로 설정

        // artistMemberDAO를 사용하여 회원 정보 저장
        int result = enterpriseMemberDAO.insert(enterpriseMemberDTO);
        return result == 1;
    }

    public List<CommonMemberDTO> viewAllCommons(){
        return enterpriseMemberDAO.getCommonMembers();
    };

    public List<ArtistMemberDTO> viewAllArtists(){
        return enterpriseMemberDAO.getArtistMembers();
    };

    public List<EnterpriseMemberDTO> viewAllEnters(){
        return enterpriseMemberDAO.getEntMembers();
    };

    public List<CommonMemberDTO> searchCommons(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return enterpriseMemberDAO.searchCommonMembers(params);
    };

    public List<ArtistMemberDTO> searchArtists(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return enterpriseMemberDAO.searchArtistMembers(params);
    };

    public List<EnterpriseMemberDTO> searchEnters(String column, String keyword){
        Map<String, String> params = new HashMap<>();
        params.put("column", column);
        params.put("keyword", keyword);
        return enterpriseMemberDAO.searchEntMembers(params);
    };

    public List<SmallConcertDTO> viewEntSmallConcert(String smallConcertId) {

        return enterpriseMemberDAO.getEntSmallConcerts(smallConcertId);
    }

    public List<SmallConcertDTO> searchEntSmallConcert(String enterId, String column, String keyword) {

        return enterpriseMemberDAO.searchEntSmallConcerts(enterId, column, keyword);
    }

    //회원정보 수정
    public void modifyEnterWithoutImage(EnterpriseMemberDTO enterpriseMemberDTO){
        enterpriseMemberDAO.modifyEnterWithoutImage(enterpriseMemberDTO);
    }

    public void enterModify(EnterpriseMemberDTO enterpriseMemberDTO, MultipartFile imgFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = imgFile.getContentType();

        //이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                imgFile.getInputStream()
        );
        enterpriseMemberDTO.setImage(uuid);

        enterpriseMemberDAO.enterModify(enterpriseMemberDTO);
    }

    public List<ReservationDTO> searchReservationsByEnterId(String enterId, String column, String keyword) {
        return enterpriseMemberDAO.searchReservationsByEnterId(enterId, column, keyword);
    }

    public List<ReservationDTO> getReservationsByEnterId(String enterId) {
        return enterpriseMemberDAO.getReservationsByEnterId(enterId);
    }

    public SmallConcertDTO getSmallConcertByAll(String name,int price,String startDate,String lastDate){
        return enterpriseMemberDAO.getSmallConcertByAll(name,price,startDate,lastDate);
    };

    public void saveSchedules(int conId,String conDate){
        enterpriseMemberDAO.saveSchedules(conId,conDate);
    };

    public void saveConcertWithoutImage(SmallConcertDTO smallConcertDTO){
        enterpriseMemberDAO.saveConcertWithoutImage(smallConcertDTO);
    }

    public void saveConcert(SmallConcertDTO smallConcertDTO, MultipartFile imgFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = imgFile.getContentType();

        //이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                imgFile.getInputStream()
        );
        smallConcertDTO.setImage(uuid);
        enterpriseMemberDAO.saveConcert(smallConcertDTO);
    }

    public int deleteSmallConcertByEnt(int conId){

        List<String> sId = enterpriseMemberDAO.getScheduleId(conId);
        
        List<String> rId = enterpriseMemberDAO.getReservationId(sId);

        if(rId.toArray().length != 0){

            enterpriseMemberDAO.deleteTickets(rId);

            enterpriseMemberDAO.deleteReservations(sId);

            enterpriseMemberDAO.deleteSchedules(conId);

            enterpriseMemberDAO.deleteSmallConcert(conId);
        }else{
            enterpriseMemberDAO.deleteSchedules(conId);

            enterpriseMemberDAO.deleteSmallConcert(conId);
        }


        return 1;
    }
}







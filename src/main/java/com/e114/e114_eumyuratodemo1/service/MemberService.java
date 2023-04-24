package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.EnterpriseMemberDTO;
import com.e114.e114_eumyuratodemo1.dto.MemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    public void insertMember(MemberDTO memberDTO) {
        memberDAO.save(memberDTO);
    }

    public MemberDTO getMemberById(String id) {
        return memberDAO.findById(id);
    }

    public MemberDTO login(String id, String pwd) {
        MemberDTO memberDTO = memberDAO.findById(id);
        if (memberDTO != null && memberDTO.getPwd().equals(pwd)) {
            return memberDTO;
        } else {
            return null;
        }
    }

    public MemberDTO getArtistMemberById(String id) {
        MemberDTO artistMemberDTO = artistMemberDAO.findById(id);
        if (artistMemberDTO == null) {
            // 예외 처리 등을 수행할 수 있음
            return null;
        }
        return artistMemberDTO;
    }

    public void insertCommonMember(CommonMemberDTO commonMemberDTO) {
        commonMemberDAO.save(commonMemberDTO);
    }

    public MemberDTO getCommonMemberById(String id) {
        MemberDTO commonMemberDTO = commonMemberDAO.findById(id);
        if (commonMemberDTO == null) {
            // 예외 처리 등을 수행할 수 있음
            return null;
        }
        return commonMemberDTO;
    }

    public void insertEnterpriseMember(EnterpriseMemberDTO enterpriseMemberDTO) {
        enterpriseMemberDAO.save(enterpriseMemberDTO);
    }

    public MemberDTO getEnterpriseMemberById(String id) {
        MemberDTO enterpriseMemberDTO = enterpriseMemberDAO.findById(id);
        if (enterpriseMemberDTO == null) {
            // 예외 처리 등을 수행할 수 있음
            return null;
        }
        return enterpriseMemberDTO;
    }

}



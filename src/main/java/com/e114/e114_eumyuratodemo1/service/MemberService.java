package com.e114.e114_eumyuratodemo1.service;

import com.e114.e114_eumyuratodemo1.dto.*;
import com.e114.e114_eumyuratodemo1.jdbc.ArtistMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import com.e114.e114_eumyuratodemo1.jdbc.EnterpriseMemberDAO;
import com.e114.e114_eumyuratodemo1.mapper.ArtistMemberMapper;
import com.e114.e114_eumyuratodemo1.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.Random;


@Service
public class MemberService {

    @Autowired
    private CommonMemberDAO commonMemberDAO;

    @Autowired
    private ArtistMemberDAO artistMemberDAO;

    @Autowired
    private EnterpriseMemberDAO enterpriseMemberDAO;

    @Autowired
    private JavaMailSender emailSender;

    public Member findMemberByEmail(String email) {
        CommonMemberDTO commonMember = commonMemberDAO.findByEmail(email);
        if (commonMember != null) {
            return new Member(commonMember.getId(), commonMember.getName(), commonMember.getEmail(), "common");
        }
        ArtistMemberDTO artistMember = artistMemberDAO.findByEmail(email);
        if (artistMember != null) {
            return new Member(artistMember.getId(), artistMember.getName(), artistMember.getEmail(), "artist");
        }
        EnterpriseMemberDTO enterpriseMember = enterpriseMemberDAO.findByEmail(email);
        if (enterpriseMember != null) {
            return new Member(enterpriseMember.getId(), enterpriseMember.getName(), enterpriseMember.getEmail(), "enterprise");
        }
        return null;
    }

    public void updatePassword(String id, String password) {
        CommonMemberDTO commonMember = commonMemberDAO.findById(id);
        if (commonMember != null) {
            commonMemberDAO.updatePassword(id, password);
            return;
        }
        ArtistMemberDTO artistMember = artistMemberDAO.findById(id);
        if (artistMember != null) {
            artistMemberDAO.updatePassword(id, password);
            return;
        }
        EnterpriseMemberDTO enterpriseMember = enterpriseMemberDAO.findById(id);
        if (enterpriseMember != null) {
            enterpriseMemberDAO.updatePassword(id, password);
            return;
        }
    }

    public String generateTempPassword() {
        int length = 12; // 임시 비밀번호 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 사용 가능한 문자열
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }


    public void sendTempPasswordByEmail(String email, String tempPassword) {
        String from = ""; // 발신자 이메일 주소
        String pwd = ""; // 발신자 이메일 비밀번호
        String to = email; // 수신자 이메일 주소

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP 서버 호스트
        properties.put("mail.smtp.port", "587"); // Gmail SMTP 서버 포트
        properties.put("mail.smtp.starttls.enable", "true"); // SSL/TLS 사용 여부
        properties.put("mail.smtp.auth", "true"); // 인증 사용 여부
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // SSL/TLS 사용 시 신뢰할 수 있는 호스트 설정

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pwd);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("임시 비밀번호 발급 안내");

            // HTML 형식으로 이메일 작성
            String htmlContent = "<html><body>"
                    + "<h3>임시 비밀번호 발급 안내</h3>"
                    + "<p>아래의 임시 비밀번호를 사용하여 로그인해주세요.</p>"
                    + "<p>임시 비밀번호: " + tempPassword + "</p>"
                    + "<p>임시 비밀번호를 이용해 로그인 한 후 꼭 비밀번호 변경 부탁드립니다.</p>"
                    + "</body></html>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //공연 랭킹
    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<SmallConcertDTO> selectTop5concert() {
        return memberMapper.selectTop5Concert();
    }

}

package com.e114.e114_eumyuratodemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CommonMemberDTO{
    private String id;
    private String pwd;
    private String name;
    private String nid;
    private String sex;
    private String birth;
    private String email;
    private String phone;
    private String road;
    private String genre;
    private int adminNum;
    private Map<String,String> favorite;
    private boolean kakao;
    private boolean google;
    private String image;
}

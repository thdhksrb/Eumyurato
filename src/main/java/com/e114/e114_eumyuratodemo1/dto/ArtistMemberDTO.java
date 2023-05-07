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
public class ArtistMemberDTO {
    private String id;
    private String pwd;
    private String name;
    private String nid;
    private String sex;
    private String birth;
    private String email;
    private String phone;
    private String genre;
    private int adminNum;
    private Map<String, String> registCon;
    private int point;
    private String image;
}

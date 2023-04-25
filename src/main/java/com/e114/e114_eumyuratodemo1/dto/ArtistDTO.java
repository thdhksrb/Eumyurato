package com.e114.e114_eumyuratodemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class ArtistDTO {
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
    private String registCon;
    private int point;
    private String image;

}

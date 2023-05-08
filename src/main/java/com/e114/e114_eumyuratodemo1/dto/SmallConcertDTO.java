package com.e114.e114_eumyuratodemo1.dto;

import lombok.Data;

@Data
public class SmallConcertDTO {

    private int id;
    private String enterId;
    private String name;
    private String location;
    private String regDate;
    private String pname;
    private int viewCount;
    private int price;
    private String startDate;
    private String lastDate;
    private String image;
    private byte[] imageByteArray;
}

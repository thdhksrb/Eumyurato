package com.e114.e114_eumyuratodemo1.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InfoDTO {
    private int no;
    private String title;
    private String content;
    private String date_created;
}

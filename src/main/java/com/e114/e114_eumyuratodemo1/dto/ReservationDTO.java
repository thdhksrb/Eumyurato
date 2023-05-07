package com.e114.e114_eumyuratodemo1.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private int id;
    private int sid;
    private String cid;
    private String payTime;
    private String viewDate;
    private int memberNum;
    private int reservPay;
}

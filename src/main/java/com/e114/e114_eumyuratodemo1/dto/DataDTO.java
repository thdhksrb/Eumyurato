package com.e114.e114_eumyuratodemo1.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class DataDTO {

    private Map<String,Object> myData;
}

package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDAO {
    public List<SmallConcertDTO> viewSmallConcert();
    public SmallConcertDTO selectConcert(int id);
    public SchedulesDTO selectConcertTime(int id, String conDate);
    public List<String> selectBooked(int conId,String conDate);
    public int insertSeat(Map<String, Object> map);

}

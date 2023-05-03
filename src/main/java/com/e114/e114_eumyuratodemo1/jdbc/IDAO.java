package com.e114.e114_eumyuratodemo1.jdbc;

import com.e114.e114_eumyuratodemo1.dto.BuskingDTO;
import com.e114.e114_eumyuratodemo1.dto.LocalFestivalDTO;
import com.e114.e114_eumyuratodemo1.dto.SchedulesDTO;
import com.e114.e114_eumyuratodemo1.dto.SmallConcertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDAO {
    public List<SmallConcertDTO> viewSmallConcert();
    public List<BuskingDTO> viewBusking();
    public List<LocalFestivalDTO> viewLocalFestival();

    public SchedulesDTO selectConcertTime(int id, String conDate);

    public List<String> selectBooked(int conId,String conDate);

    public int insertSeat(Map<String, Object> map);

    public int deleteSeat(Map<String, Object> map);

    public LocalFestivalDTO selectLocal(int id);
    public SmallConcertDTO selectConcert(int id);
    public BuskingDTO selectBusking(int id);

    public int upViewCountSmallConcert(int id);
    public int upViewCountBusking(int id);
    public int upViewCountLocalFestival(int id);

    public int saveDonation(int price, int id);
    public int saveDonationNum(int price, int id, String userId);

}

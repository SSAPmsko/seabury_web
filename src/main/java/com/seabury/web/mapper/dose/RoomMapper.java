package com.seabury.web.mapper.dose;

import com.seabury.web.entity.dose.RoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    List<RoomEntity> getRoomList(RoomEntity RoomEntity);

    int insertRoom(RoomEntity RoomEntity);
    int updateRoom(RoomEntity RoomEntity);
    int deleteRoom(RoomEntity RoomEntity);
}

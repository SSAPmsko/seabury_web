package com.seabury.web.service;

import com.seabury.web.entity.dose.RoomEntity;

import java.util.List;

public interface RoomService {

    public List<RoomEntity> getRoomList(RoomEntity roomEntity);

    public int insertRoom(RoomEntity roomEntity);
    public int updateRoom(RoomEntity roomEntity);
    public int deleteRoom(RoomEntity roomEntity);
}

package com.seabury.web.service;

import com.seabury.web.entity.dose.RoomEntity;
import com.seabury.web.mapper.dose.RoomMapper;
import com.seabury.web.vo.dose.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomMapper roomMapper;

    @Override
    public List<RoomEntity> getRoomList(RoomEntity roomEntity) {
        return roomMapper.getRoomList(roomEntity);
    }

    @Override
    public int insertRoom(RoomEntity roomEntity) {
        return roomMapper.insertRoom(roomEntity);
    }

    @Override
    public int updateRoom(RoomEntity roomEntity) {
        return roomMapper.updateRoom(roomEntity);
    }

    @Override
    public int deleteRoom(RoomEntity roomEntity) { return roomMapper.deleteRoom(roomEntity);}

    public RoomVO ConvertEntityToVO(RoomEntity roomEntity) {
        RoomVO roomVO = new RoomVO();

        if (roomEntity != null) {
            roomVO.setID(roomEntity.getID());
            roomVO.setName(roomEntity.getName());
            roomVO.setOperator(roomEntity.getOperator());
            roomVO.setDescription(roomEntity.getDescription());
            roomVO.setProjectID(roomEntity.getProjectID());
            roomVO.setProjectName(roomEntity.getProjectName());

        }
        return roomVO;
    }

    public RoomEntity ConvertVOToEntity(RoomVO roomVO) {
        RoomEntity roomEntity = new RoomEntity();

        if (roomVO != null){
            roomEntity.setID(roomVO.getID());
            roomEntity.setName(roomVO.getName());
            roomEntity.setOperator(roomVO.getOperator());
            roomEntity.setDescription(roomVO.getDescription());
            roomEntity.setProjectID(roomVO.getProjectID());
            roomEntity.setProjectName(roomVO.getProjectName());

        }
        return roomEntity;
    }
}

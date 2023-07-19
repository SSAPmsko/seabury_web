package com.seabury.web.service;

import com.seabury.web.entity.EquipmentEntity;
import com.seabury.web.entity.ShieldEntity;
import com.seabury.web.mapper.EquipmentMapper;
import com.seabury.web.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShieldServiceImpl implements ShieldService {

    @Override
    public List<ShieldEntity> getShieldList(ShieldEntity shieldEntity) {
        return null;
    }

    @Override
    public int insertShield(ShieldEntity shieldEntity) {
        return 0;
    }

    @Override
    public int updateShield(ShieldEntity shieldEntity) {
        return 0;
    }

    @Override
    public int deleteShield(ShieldEntity shieldEntity) {
        return 0;
    }
}

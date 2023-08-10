package com.seabury.web.service;

import com.seabury.web.entity.dose.ShieldEntity;

import java.util.List;

public interface ShieldService {
    public List<ShieldEntity> getShieldList(ShieldEntity shieldEntity);

    public int insertShield(ShieldEntity shieldEntity);
    public int updateShield(ShieldEntity shieldEntity);
    public int deleteShield(ShieldEntity shieldEntity);
}

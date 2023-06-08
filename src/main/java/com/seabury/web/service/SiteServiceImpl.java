package com.seabury.web.service;

import com.seabury.web.entity.SiteEntity;
import com.seabury.web.mapper.SiteMapper;
import com.seabury.web.vo.SiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    SiteMapper SiteMapper;

    @Override
    public List<SiteEntity> getSiteList(SiteEntity SiteEntity) {
        return SiteMapper.getSiteList(SiteEntity);
    }

    @Override
    public int insertSite(SiteEntity SiteEntity) {
        return SiteMapper.insertSite(SiteEntity);
    }

    @Override
    public int updateSite(SiteEntity SiteEntity) {
        return SiteMapper.updateSite(SiteEntity);
    }

    @Override
    public int deleteSite(SiteEntity SiteEntity) {
        return SiteMapper.deleteSite(SiteEntity);
    }

    public SiteVO ConvertEntityToVO(SiteEntity SiteEntity) {
        SiteVO SiteVO = new SiteVO();

        if (SiteEntity != null) {
            SiteVO.setID(SiteEntity.getID());
            SiteVO.setImage(SiteEntity.getImage());
            SiteVO.setOperator(SiteEntity.getOperator());
            SiteVO.setReactorType(SiteEntity.getReactorType());
            SiteVO.setReactorSupplier(SiteEntity.getReactorSupplier());
            SiteVO.setConstructionBegan(SiteEntity.getConstructionBegan());
            SiteVO.setCommissionDate(SiteEntity.getCommissionDate());
            SiteVO.setDecommissionDate(SiteEntity.getDecommissionDate());
            SiteVO.setStatus(SiteEntity.getStatus());
        }
        return SiteVO;
    }

    public SiteEntity ConvertVOToEntity(SiteVO SiteVO) {
        SiteEntity SiteEntity = new SiteEntity();

        if (SiteVO != null){
            SiteEntity.setID(SiteVO.getID());
            SiteEntity.setImage(SiteVO.getImage());
            SiteEntity.setOperator(SiteVO.getOperator());
            SiteEntity.setReactorType(SiteVO.getReactorType());
            SiteEntity.setReactorSupplier(SiteVO.getReactorSupplier());
            SiteEntity.setConstructionBegan(SiteVO.getConstructionBegan());
            SiteEntity.setCommissionDate(SiteVO.getCommissionDate());
            SiteEntity.setDecommissionDate(SiteVO.getDecommissionDate());
            SiteEntity.setStatus(SiteVO.getStatus());
        }
        return SiteEntity;
    }
}

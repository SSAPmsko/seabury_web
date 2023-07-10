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
    SiteMapper siteMapper;

    @Override
    public List<SiteEntity> getSiteList(SiteEntity siteEntity) {
        return siteMapper.getSiteList(siteEntity);
    }

    @Override
    public int insertSite(SiteEntity siteEntity) {
        return siteMapper.insertSite(siteEntity);
    }

    @Override
    public int updateSite(SiteEntity siteEntity) {
        return siteMapper.updateSite(siteEntity);
    }

    @Override
    public int deleteSite(SiteEntity siteEntity) {
        return siteMapper.deleteSite(siteEntity);
    }

    public SiteVO ConvertEntityToVO(SiteEntity siteEntity) {
        SiteVO siteVO = new SiteVO();

        if (siteEntity != null) {
            siteVO.setID(siteEntity.getID());
            siteVO.setImage(siteEntity.getImage());
            siteVO.setOperator(siteEntity.getOperator());
            siteVO.setReactorType(siteEntity.getReactorType());
            siteVO.setReactorSupplier(siteEntity.getReactorSupplier());
            siteVO.setConstructionBegan(siteEntity.getConstructionBegan());
            siteVO.setCommissionDate(siteEntity.getCommissionDate());
            siteVO.setDecommissionDate(siteEntity.getDecommissionDate());
            siteVO.setStatus(siteEntity.getStatus());
        }
        return siteVO;
    }

    public SiteEntity ConvertVOToEntity(SiteVO siteVO) {
        SiteEntity siteEntity = new SiteEntity();

        if (siteVO != null){
            siteEntity.setID(siteVO.getID());
            siteEntity.setImage(siteVO.getImage());
            siteEntity.setOperator(siteVO.getOperator());
            siteEntity.setReactorType(siteVO.getReactorType());
            siteEntity.setReactorSupplier(siteVO.getReactorSupplier());
            siteEntity.setConstructionBegan(siteVO.getConstructionBegan());
            siteEntity.setCommissionDate(siteVO.getCommissionDate());
            siteEntity.setDecommissionDate(siteVO.getDecommissionDate());
            siteEntity.setStatus(siteVO.getStatus());
        }
        return siteEntity;
    }
}

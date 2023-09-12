package com.seabury.web.mapper.dose;

import com.seabury.web.entity.dose.SiteEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface SiteMapper {

    List<SiteEntity> getSiteList(SiteEntity SiteEntity);

    int insertSite(SiteEntity SiteEntity);
    int updateSite(SiteEntity SiteEntity);
    int deleteSite(SiteEntity SiteEntity);
}

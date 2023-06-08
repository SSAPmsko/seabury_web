package com.seabury.web.service;

import com.seabury.web.entity.SiteEntity;

import java.util.List;

public interface SiteService {
    public List<SiteEntity> getSiteList(SiteEntity SiteEntity);

    public int insertSite(SiteEntity SiteEntity);
    public int updateSite(SiteEntity SiteEntity);
    public int deleteSite(SiteEntity SiteEntity);
}

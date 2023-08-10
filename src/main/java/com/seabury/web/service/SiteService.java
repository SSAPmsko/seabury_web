package com.seabury.web.service;

import com.seabury.web.entity.dose.SiteEntity;

import java.util.List;

public interface SiteService {
    public List<SiteEntity> getSiteList(SiteEntity siteEntity);

    public int insertSite(SiteEntity siteEntity);
    public int updateSite(SiteEntity siteEntity);
    public int deleteSite(SiteEntity siteEntity);
}

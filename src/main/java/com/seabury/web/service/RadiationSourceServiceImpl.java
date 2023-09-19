package com.seabury.web.service;

import java.util.List;
import java.util.Map;

import com.seabury.web.entity.integrated.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.seabury.web.mapper.integrated.RadiationSourceMapper;


@Service
public class RadiationSourceServiceImpl implements RadiationSourceService {

	@Autowired
	@Qualifier(value = "integratedSqlSession")
	private SqlSession integratedSqlSession;

	final String INTEGRATED_RADIATIONSOURCEMAPPER_PATH = "com.seabury.web.mapper.integrated.RadiationSourceMapper";

	@Autowired
	RadiationSourceMapper radiationsourceMapper;
	
	@Override
	public List<ND_60_103_INH_Entity> getND60_103_IHN_List(Map<String, String> ParamSelect) {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetND60_103_IHN_List", ParamSelect);
	}
	
	@Override
	public List<ND_60_103_ING_Entity> getND60_103_ING_List(Map<String, String> ParamSelect) {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetND60_103_ING_List", ParamSelect);
	}
	
	@Override
	public List<ND_60_EXT_Entity> getND60_EXT_List(Map<String, String> ParamSelect) {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetND60_EXT_List", ParamSelect);
	}
	
	@Override
	public List<ND_103_EXT_Entity> getND103_EXT_List(Map<String, String> ParamSelect) {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetND103_EXT_List", ParamSelect);
	}
	
	@Override
	public List<ND_103_W_Entity> getND103_W_List(Map<String, String> ParamSelect) {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetND103_W_List", ParamSelect);
	}

	@Override
	public List<NuclearEntity> getNuclearDataList() {
		return integratedSqlSession.selectList(INTEGRATED_RADIATIONSOURCEMAPPER_PATH + ".GetNuclearDataList");
	}
}

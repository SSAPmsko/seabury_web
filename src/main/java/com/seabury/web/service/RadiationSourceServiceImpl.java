package com.seabury.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seabury.web.entity.ND_103_EXT_Entity;
import com.seabury.web.entity.ND_103_W_Entity;
import com.seabury.web.entity.ND_60_103_ING_Entity;
import com.seabury.web.entity.ND_60_103_INH_Entity;
import com.seabury.web.entity.ND_60_EXT_Entity;
import com.seabury.web.mapper.RadiationSourceMapper;


@Service
public class RadiationSourceServiceImpl implements RadiationSourceService {
	
	@Autowired
	RadiationSourceMapper radiationsourceMapper;
	
	@Override
	public List<ND_60_103_INH_Entity> getND60_103_IHN_List(Map<String, String> ParamSelect)
	{
		return radiationsourceMapper.GetND60_103_IHN_List(ParamSelect);
	}
	
	@Override
	public List<ND_60_103_ING_Entity> getND60_103_ING_List(Map<String, String> ParamSelect)
	{
		return radiationsourceMapper.GetND60_103_ING_List(ParamSelect);
	}
	
	@Override
	public List<ND_60_EXT_Entity> getND60_EXT_List(Map<String, String> ParamSelect)
	{
		return radiationsourceMapper.GetND60_EXT_List(ParamSelect);
	}
	
	@Override
	public List<ND_103_EXT_Entity> getND103_EXT_List(Map<String, String> ParamSelect)
	{
		return radiationsourceMapper.GetND103_EXT_List(ParamSelect);
	}
	
	@Override
	public List<ND_103_W_Entity> getND103_W_List(Map<String, String> ParamSelect)
	{
		return radiationsourceMapper.GetND103_W_List(ParamSelect);
	}
}

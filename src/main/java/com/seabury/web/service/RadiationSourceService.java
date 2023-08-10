package com.seabury.web.service;

import java.util.List;
import java.util.Map;

import com.seabury.web.entity.integrated.ND_103_EXT_Entity;
import com.seabury.web.entity.integrated.ND_103_W_Entity;
import com.seabury.web.entity.integrated.ND_60_103_ING_Entity;
import com.seabury.web.entity.integrated.ND_60_103_INH_Entity;
import com.seabury.web.entity.integrated.ND_60_EXT_Entity;


public interface RadiationSourceService {
	public List<ND_60_103_INH_Entity> getND60_103_IHN_List(Map<String, String> ParamSelect);
	
	public List<ND_60_103_ING_Entity> getND60_103_ING_List(Map<String, String> ParamSelect);
	
	public List<ND_60_EXT_Entity> getND60_EXT_List(Map<String, String> ParamSelect);
	
	public List<ND_103_EXT_Entity> getND103_EXT_List(Map<String, String> ParamSelect);
	
	public List<ND_103_W_Entity> getND103_W_List(Map<String, String> ParamSelect);
}

package com.seabury.web.mapper.integrated;

import java.util.List;
import java.util.Map;

import com.seabury.web.entity.integrated.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RadiationSourceMapper {

	public List<ND_60_103_INH_Entity> GetND60_103_IHN_List(Map<String, String> ParamSelect);
	
	public List<ND_60_103_ING_Entity> GetND60_103_ING_List(Map<String, String> ParamSelect);
	
	public List<ND_60_EXT_Entity> GetND60_EXT_List(Map<String, String> ParamSelect);
	
	public List<ND_103_EXT_Entity> GetND103_EXT_List(Map<String, String> ParamSelect);
	
	public List<ND_103_W_Entity> GetND103_W_List(Map<String, String> ParamSelect);

	public List<NuclearEntity> GetNuclearDataList();
}

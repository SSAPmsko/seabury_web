package com.seabury.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seabury.web.entity.ND_60_103_ING_Entity;
import com.seabury.web.entity.ND_60_103_INH_Entity;
import com.seabury.web.service.RadiationSourceService;

@RestController
public class RadiationSourceController {

	@Resource
	RadiationSourceService radiationsourceService;
	
	@PostMapping(value="/radiationsource/getND_60_103_INH_List")
	public @ResponseBody List<ND_60_103_INH_Entity> getND_60_103_INH_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
				
		String tb_name = (String)message.get("tbname");
		
		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);
		
		List<ND_60_103_INH_Entity> ND_60_INH_IN_List = radiationsourceService.getND60_103_IHN_List(ParamSelect);

		System.out.println(ND_60_INH_IN_List.size());

		return ND_60_INH_IN_List;
	}	

	@PostMapping(value="/radiationsource/getND_60_103_ING_List")
	public @ResponseBody List<ND_60_103_ING_Entity> getND_60_103_ING_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
				
		String tb_name = (String)message.get("tbname");
		
		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);
		
		List<ND_60_103_ING_Entity> ND_60_ING_IN_List = radiationsourceService.getND60_103_ING_List(ParamSelect);

		System.out.println(ND_60_ING_IN_List.size());

		return ND_60_ING_IN_List;
	}
}

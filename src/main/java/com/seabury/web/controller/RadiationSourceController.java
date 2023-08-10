package com.seabury.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seabury.web.entity.integrated.*;
import com.seabury.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.seabury.web.service.RadiationSourceService;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RadiationSourceController {
	@Autowired
	CommonService commonService;

	@Resource
	RadiationSourceService radiationsourceService;

	/* kyc 0612 - Add Reference */
	@GetMapping(value="/nd_60_103_inh")
	public ModelAndView radiationsource_nd_60_103_inh(ModelAndView mav){
		mav.setViewName("view/sub/radiationsource/nd_60_103_inh");
		return mav;
	}

	@GetMapping(value="/nd_60_103_ing")
	public ModelAndView radiationsource_nd_60_103_ing(ModelAndView mav){
		mav.setViewName("view/sub/radiationsource/nd_60_103_ing");
		return mav;
	}

	@PostMapping(value="/nd_60_103_ing")
	public void radiationsource_nd_60_103_inh(HttpServletRequest request, HttpServletResponse response){
		// 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
		try {
			commonService.sendRedirect(request, response, "view/sub/radiationsource/nd_60_103_ing");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value="/nd_103_ext")
	public ModelAndView radiationsource_nd_103_ext(ModelAndView mav){
		mav.setViewName("view/sub/radiationsource/nd_103_ext");
		return mav;
	}

	@GetMapping(value="/nd_60_ext")
	public ModelAndView radiationsource_nd_60_ext(ModelAndView mav){
		mav.setViewName("view/sub/radiationsource/nd_60_ext");
		return mav;
	}

	@GetMapping(value="/nd_103_w")
	public ModelAndView radiationsource_nd_103_w(ModelAndView mav){
		mav.setViewName("view/sub/radiationsource/nd_103_w");
		return mav;
	}
	
	@PostMapping(value="/getND_60_103_INH_List")
	public @ResponseBody List<ND_60_103_INH_Entity> getND_60_103_INH_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
				
		String tb_name = (String)message.get("tbname");
		
		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);
		
		List<ND_60_103_INH_Entity> ND_60_INH_IN_List = radiationsourceService.getND60_103_IHN_List(ParamSelect);

		System.out.println(ND_60_INH_IN_List.size());

		return ND_60_INH_IN_List;
	}	

	@PostMapping(value="/getND_60_103_ING_List")
	public @ResponseBody List<ND_60_103_ING_Entity> getND_60_103_ING_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
				
		String tb_name = (String)message.get("tbname");
		
		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);
		
		List<ND_60_103_ING_Entity> ND_60_ING_IN_List = radiationsourceService.getND60_103_ING_List(ParamSelect);

		System.out.println(ND_60_ING_IN_List.size());

		return ND_60_ING_IN_List;
	}

	@PostMapping(value="/getND_60_EXT_List")
	public @ResponseBody List<ND_60_EXT_Entity> getND_60_EXT_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {

		String tb_name = (String)message.get("tbname");

		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);

		List<ND_60_EXT_Entity> ND_60_EXT_List = radiationsourceService.getND60_EXT_List(ParamSelect);

		System.out.println(ND_60_EXT_List.size());

		return ND_60_EXT_List;
	}

	@PostMapping(value="/getND_103_W_List")
	public @ResponseBody List<ND_103_W_Entity> getND_103_W_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {

		String tb_name = (String)message.get("tbname");

		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);

		List<ND_103_W_Entity> ND_103_W_List = radiationsourceService.getND103_W_List(ParamSelect);

		System.out.println(ND_103_W_List.size());

		return ND_103_W_List;
	}

	@PostMapping(value="/getND_103_EXT_List")
	public @ResponseBody List<ND_103_EXT_Entity> getND_103_EXT_List(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {

		String tb_name = (String)message.get("tbname");

		Map<String, String> ParamSelect = new HashMap<>();

		ParamSelect.put("table_name", tb_name);

		List<ND_103_EXT_Entity> ND_103_EXT_List = radiationsourceService.getND103_EXT_List(ParamSelect);

		System.out.println(ND_103_EXT_List.size());

		return ND_103_EXT_List;
	}
}

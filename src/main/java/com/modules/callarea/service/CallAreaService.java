package com.modules.callarea.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.base.dao.BaseMapper;

/**
 * 归属地
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
@Service
public class CallAreaService {

	@Autowired
	private BaseMapper baseMapper;
	
	public List<Map<String,Object>> searchArea(String phone){
		return baseMapper.select("select * from call_area where  number='"+phone.substring(0,7)+"'");
	}
}

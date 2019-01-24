package com.modules.jantest.service;

import com.modules.jantest.dao.JanTestMapper;
import com.modules.jantest.bean.JanTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.modules.base.dao.BaseMapper;
import com.commons.apiresult.ApiResult;
import com.commons.utils.PageMode;

/**
* Author DoubleLi
* Date  2019-01-24
*/
@Service("janTestService")
public class JanTestService {
	
    @Autowired
    private JanTestMapper janTestMapper;
	@Autowired
	private BaseMapper baseMapper;
	
	public void searchPage(ApiResult apiResult, PageMode pageMode, JanTest janTest) {
		pageMode.setSqlFrom("select * from JanTest");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, data);
	}
	
    public JanTest get(Integer id){
        return janTestMapper.get(id);
    }

    public List<JanTest> findList(JanTest janTest) {
        return janTestMapper.findList(janTest);
    }

    public List<JanTest> findAllList() {
        return janTestMapper.findAllList();
    }

    public Integer insert(JanTest janTest) {
         janTestMapper.insert(janTest);
         return janTest.getId();
    }

    public int insertBatch(List<JanTest> janTests){
        return janTestMapper.insertBatch(janTests);
    }

    public int update(JanTest janTest) {
        return janTestMapper.update(janTest);
    }

    public int delete(Integer id) {
        return janTestMapper.delete(id);
    }

}

package com.modules.jantest.dao;

import com.modules.jantest.bean.JanTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Author DoubleLi
* Date  2019-01-24
*/
@Mapper
public interface JanTestMapper {

    public JanTest get(Integer id);

    public List<JanTest> findList(JanTest janTest);

    public List<JanTest> findAllList();

    public void insert(JanTest janTest);

    public int insertBatch(List<JanTest> janTests);

    public int update(JanTest janTest);

    public int delete(Integer id);

}
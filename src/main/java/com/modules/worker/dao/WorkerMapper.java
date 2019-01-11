package com.modules.worker.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.modules.worker.bean.Worker;

@Mapper
public interface WorkerMapper {

	public Worker login(@Param("username")String username,@Param("psw") String psw); 
	
	public Integer updateWorkerOnlineTime(@Param("workerId")String workerId);
	
	//公用查询
	public List<Worker> selWorker(Map<String, Object> map);
	
	public Integer addwoker(Worker worker);
	
	public Integer delWorker(Integer[] ids);
	
	public Integer updateWorker(Worker worker);
	
	public Integer updatePsw(@Param("psw")String psw, @Param("id")Integer id);

	/**
	 * 人员详情
	 * @param id
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	public Worker getWorker(@Param("id")Integer id);

	/**
	 * @param workerId
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	public Worker getWorkerWorkerId(@Param("workerId")String workerId);

	/**
	 * @param lineNum
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	public Worker getInfoLineNum(Integer lineNum);
}

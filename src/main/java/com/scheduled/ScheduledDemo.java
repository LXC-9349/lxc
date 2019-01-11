/**
 * 
 * @author DoubleLi
 * @time 2019年1月7日
 * 
 */
package com.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 工单通知定时任务
 * 
 * @author DoubleLi
 * @time 2019年1月7日
 * 
 */
@Component
public class ScheduledDemo {
	private static final Logger log = LoggerFactory.getLogger(ScheduledDemo.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	public RedisTemplate redisTemplate;//由于是轮循用缓存比较好
	/**
	 * 定时处理工单通知
	 * 
	 * @time 2019年1月7日
	 * @author DoubleLi
	 */
	@Scheduled(fixedRate = 10000)
	public void test() {
		try {
		 
		} catch (Exception e) {
			log.warn(" 定时任务", e);
		}
	}

}
package com.listeners;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;*/
/**
 * 这个是用于监听redis key值过期
 * 
 * @author LXC
 */
/*@Component
public class KeyExpiredListener implements MessageListener {

	private final static Logger LOG = LoggerFactory.getLogger(KeyExpiredListener.class);
	@Autowired
	private RedisTemplate<?, ?> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("onPMessage pattern " + pattern + " " + " " + message);
		String channel = new String(message.getChannel());
		String str = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
		LOG.info("received message channel {}, body is {}.", channel, str);
	}

}*/

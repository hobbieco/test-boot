package hobbieco.test.app.redis.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hobbieco.test.common.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

@Slf4j
@Service
public class JedisSubService {

	@Resource(name="randomUtil")
	private RandomUtil randomUtil;
	
	@Resource(name="jedisPool")
	private List<JedisPool> pool;
	
	@Async
	public void setStringAll(int i) {
		for (int j=0; j<10; j++) {
			String key = StringUtils.joinWith(":", "TEST","STRING",i,j);
			String value = randomUtil.getRandomString(j%8);
			Jedis jedis = pool.get(j%8).getResource();
			jedis.set(key, value);
			log.debug("jedis set string key : {}",key);
			jedis.close();
		}
	}
	
	@Async
	public void setStringAllPipeline(int i) {
		Jedis jedis = pool.get(i%8).getResource();
		Pipeline pipeline = jedis.pipelined();
		for (int j=0; j<10; j++) {
			String key = StringUtils.joinWith(":", "TEST","STRING",i,j);
			String value = randomUtil.getRandomString(j%8);
			pipeline.set(key, value);
			log.debug("jedis set string key : {}",key);
		}
		pipeline.sync();
		jedis.close();
	}
}

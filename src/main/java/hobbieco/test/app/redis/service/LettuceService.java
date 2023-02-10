package hobbieco.test.app.redis.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hobbieco.test.common.RandomUtil;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LettuceService {

	@Resource(name="randomUtil")
	private RandomUtil randomUtil;
	
	@Resource(name="lettuceAsync")
	private List<RedisAsyncCommands<String,String>> cmdList;
	
//	@Resource(name="lettucePool")
//	private GenericObjectPool<StatefulRedisConnection<String, String>> pool;
	
	public void setString(int number, String loop) {
		log.debug("lettuce async number : {} / loop count : {}",number,loop);
		RedisAsyncCommands<String,String> command = cmdList.get(number%8);
		for (int i=0; i<10; i++) {
			command.set(StringUtils.joinWith(":", "TEST","STRING",number,i), randomUtil.getRandomString(i));
		}
	}
	
	public void setStringPipeline(int number, String loop) {
		log.debug("lettuce async pipeline number : {} / loop count : {}",number,loop);
		RedisAsyncCommands<String,String> command = cmdList.get(number%8);
		command.setAutoFlushCommands(false);
		
		for (int i=0; i<10; i++) {
			command.set(StringUtils.joinWith(":", "TEST","STRING",number,i), randomUtil.getRandomString(i));
		}
		
		command.flushCommands();
		command.setAutoFlushCommands(true);
	}
	
//	public void setStringPool(int number, String loop) {
//		log.debug("lettuce async pipeline number : {} / loop count : {}",number,loop);
//		try {
//			StatefulRedisConnection<String, String> connection = pool.borrowObject();
//			RedisAsyncCommands<String,String> command = connection.async();
//			for (int i=0; i<10; i++) {
//				command.set(StringUtils.joinWith(":", "TEST","STRING",number,i), randomUtil.getRandomString(i));
//			}
//			
//		} catch (Exception e) {
//			log.debug("get lettuce pool connection failed");
//		}
//	}
}

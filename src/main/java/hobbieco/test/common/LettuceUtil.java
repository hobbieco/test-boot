package hobbieco.test.common;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class LettuceUtil {
	
	@Resource(name="lettuceConnection")
	private StatefulRedisConnection<String,String> connection;

	public RedisAsyncCommands<String,String> getAsync() {
		return connection.async();
	}
	
	public RedisCommands<String, String> getSync() {
		return connection.sync();
	}
}

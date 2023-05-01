package hobbieco.test.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hobbieco.test.config.props.LettuceProps;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

//@Configuration
public class LettuceConfig {

	@Resource(name="lettuceProps")
	private LettuceProps props;
	
	@Bean
	StatefulRedisConnection<String,String> lettuceConnection() {
		StatefulRedisConnection<String,String> connection = null;
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		RedisClient client = RedisClient.create(uri);
		connection = client.connect();
		return connection;
	}

}

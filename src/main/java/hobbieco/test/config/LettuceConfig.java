package hobbieco.test.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hobbieco.test.config.props.LettuceProps;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

@Configuration
public class LettuceConfig {

	@Resource(name="lettuceProps")
	private LettuceProps props;
	
	@Bean
	List<RedisAsyncCommands<String,String>> lettuceAsync() {
		List<RedisAsyncCommands<String,String>> list = new ArrayList<>();
		list.add(lettuce00());
		list.add(lettuce01());
		list.add(lettuce02());
		list.add(lettuce03());
		list.add(lettuce04());
		list.add(lettuce05());
		list.add(lettuce06());
		list.add(lettuce07());
		return list;
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce00() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce01() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce02() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce03() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce04() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce05() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce06() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
	@Bean
	RedisAsyncCommands<String,String> lettuce07() {
		RedisURI.Builder builder = RedisURI.builder();
		builder.withHost(props.getIp())
				.withPort(props.getPort())
				.withPassword(props.getAuth().toCharArray());
		RedisURI uri = builder.build();
		
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String,String> connection = client.connect();
		return connection.async();
	}
	
}

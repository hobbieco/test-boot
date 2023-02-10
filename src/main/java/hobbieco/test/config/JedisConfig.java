package hobbieco.test.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hobbieco.test.config.props.JedisProps;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class JedisConfig {

	@Resource(name="jedisProps")
	private JedisProps props;
	
	private GenericObjectPoolConfig<Jedis> genericObjectPoolConfig() {
		GenericObjectPoolConfig<Jedis> config = new GenericObjectPoolConfig<Jedis>();
		config.setMaxTotal(20);
		config.setMaxIdle(20);
		config.setMinIdle(10);
		config.setMaxWaitMillis(5000);
		return config;
	}
	
	@Bean
	List<JedisPool> jedisPool() {
		List<JedisPool> list = new ArrayList<>();
		list.add(jedisPool00());
		list.add(jedisPool01());
		list.add(jedisPool02());
		list.add(jedisPool03());
		list.add(jedisPool04());
		list.add(jedisPool05());
		list.add(jedisPool06());
		list.add(jedisPool07());
		return list;
	}

    @Bean
    JedisPool jedisPool00() {
        JedisPool pool = new JedisPool(genericObjectPoolConfig(), props.getIp(), props.getPort());
        return pool;
    }
	
	@Bean
	JedisPool jedisPool01() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool02() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool03() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool04() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool05() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool06() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}
	
	@Bean
	JedisPool jedisPool07() {
		JedisPool pool = new JedisPool(genericObjectPoolConfig(),props.getIp(),props.getPort());
		return pool;
	}

}

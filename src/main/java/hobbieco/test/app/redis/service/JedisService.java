package hobbieco.test.app.redis.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hobbieco.test.common.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanResult;

@Slf4j
@Service
public class JedisService {
	
	@Resource(name="randomUtil")
	private RandomUtil randomUtil;
	
	@Resource(name="jedisPool")
	private List<JedisPool> pool;
	
	@Resource(name="jedisSubService")
	private JedisSubService service;
	
	public String test(HttpServletRequest request) {
		String result = StringUtils.EMPTY;
		String size = request.getParameter("size");
		if (StringUtils.isNoneBlank(size)) {
			int s = Integer.parseInt(size);
			result = randomUtil.getRandomString(s);
		} else {
			result = randomUtil.getRandomString(10);
		}
		return result;
	}
	
	public String testPipeline() {
		StringBuilder result = new StringBuilder();
		Jedis jedis = null;
		Pipeline pipeline = null;
		try {
			jedis = pool.get(0).getResource();
			pipeline = jedis.pipelined();
			
			jedis.flushAll();
			
			jedis.select(1);
			jedis.set("TEST:JEDIS:0001", "test jedis value 1");
			
			
			pipeline.select(0);
			pipeline.set("TEST:PIPELINE:0000", "test pipeline value 0");
			pipeline.sync();
			
			
			jedis.set("TEST:JEDIS:0000", "test jedis value 0");
			jedis.select(1);
			log.debug("get jedis key TEST:JEDIS:0001 after pipeline sync - {}",jedis.get("TEST:JEDIS:0001"));
			jedis.select(0);
			log.debug("get jedis key TEST:JEDIS:0000 after pipeline sync - {}",jedis.get("TEST:JEDIS:0000"));
			
			pipeline.set("TEST:PIPELINE:0001", "test pipeline value 1");
			pipeline.select(1);
			pipeline.set("TEST:PIPELINE:0002", "test pipeline value 2");
			pipeline.sync();
			
			log.debug("get jedis key TEST:JEDIS:0001 after pipeline sync - {}",jedis.get("TEST:JEDIS:0001"));
			log.debug("get jedis key TEST:JEDIS:0000 after pipeline sync - {}",jedis.get("TEST:JEDIS:0000"));
			
			Response<String> res0 = pipeline.get("TEST:PIPELINE:0000");
			Response<String> res1 = pipeline.get("TEST:PIPELINE:0001");
			Response<String> res2 = pipeline.get("TEST:PIPELINE:0002");
			pipeline.sync();
			
			log.debug("get pipeline key TEST:PIPELINE:0000 - {}",res0.get());
			log.debug("get pipeline key TEST:PIPELINE:0001 - {}",res1.get());
			log.debug("get pipeline key TEST:PIPELINE:0002 - {}",res2.get());
			
			result.append("1. jedis, pipeline 혼용 가능하지만 pipeline을 sync()로 완료해야된다.");
			result.append(System.lineSeparator());
			result.append("2. pipeline을 sync()로 완료하기 전에 jedis 호출 시 Exception 발생한다.");
			result.append(System.lineSeparator());
			result.append("3. jedis, pipeline 혼용 시 db는 마지막 select된 db를 바라본다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result.toString();
	}
	
	public void delAll() {
		Jedis jedis = pool.get(randomUtil.getRandom(8)).getResource();
		String cursor = "0";
		while(true) {
			ScanResult<String> scan = jedis.scan(cursor);
			
			for (String key : scan.getResult()) {
				jedis.del(key);
				log.debug("jedis del key : {}",key);
			}
			
			cursor = scan.getCursor();
			if (StringUtils.equals("0", cursor)) {
				break;
			}
		}
		jedis.close();
	}
	
	public void setStringAll() {
		for (int i=0; i<1000; i++) {
			service.setStringAll(i);
		}
	}
	
	public void setStringAllPipeline() {
		for (int i=0; i<1000; i++) {
			service.setStringAllPipeline(i);
		}
	}
	
	public void setString(int number, String loop) {
		log.debug("jedis pool number : {} / loop count : {}",number,loop);
		Jedis jedis = pool.get(number%8).getResource();
		for (int i=0; i<10; i++) {
			jedis.set(StringUtils.joinWith(":", "TEST","STRING",number,i), randomUtil.getRandomString(i));
		}
		jedis.close();
	}
	
	public void setStringPipeline(int number, String loop) {
		log.debug("jedis pool pipeline number : {} / loop count : {}",number,loop);
		Jedis jedis = pool.get(number%8).getResource();
		Pipeline pipeline = jedis.pipelined();
		for (int i=0; i<10; i++) {
			pipeline.set(StringUtils.joinWith(":", "TEST","STRING",number,i), randomUtil.getRandomString(i));
		}
		pipeline.sync();
		jedis.close();
	}
}

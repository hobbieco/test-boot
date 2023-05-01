package hobbieco.test.app.redis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import hobbieco.test.common.LettuceUtil;
import hobbieco.test.common.RandomUtil;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Service
public class LettuceService {

	@Resource(name="randomUtil")
	private RandomUtil randomUtil;
	
	@Resource(name="lettuceUtil")
	private LettuceUtil lettuceUtil;
	
	public Map<String,Object> setString(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		int db = NumberUtils.toInt(request.getParameter("db"),-1);
		if (Range.between(-1, 16).contains(db)) {
			String key = request.getParameter("key");
			if (StringUtils.isNotBlank(key)) {
				String value = request.getParameter("value");
				RedisAsyncCommands<String,String> command = lettuceUtil.getAsync();
				List<RedisFuture<String>> futures = new ArrayList<>();
				futures.add(command.select(db));
				futures.add(command.set(key,value));
				futures.add(command.get(key));
				LettuceFutures.awaitAll(1, TimeUnit.MINUTES, futures.toArray(new RedisFuture[futures.size()]));
				
				for (RedisFuture<String> future : futures) {
					try {
						log.debug("future - {}",future.get());
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}

				result.put("value", value);
			} else {
				result.put("result", "key is blank");
			}
			result.put("key", key);
		} else {
			result.put("result", "db number not in range");
		}
		result.put("db", db);
		return result;
	}

}

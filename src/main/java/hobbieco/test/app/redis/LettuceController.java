package hobbieco.test.app.redis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.app.redis.service.LettuceService;
import hobbieco.test.config.annotation.NoRequestLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestController
//@RequestMapping(value="/redis/lettuce")
public class LettuceController {
	
	@Resource(name="lettuceService")
	private LettuceService service;
	
	@NoRequestLog
	@RequestMapping(value="/set/string",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setString(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> result = new HashMap<>();
		result = service.setString(request);
		return result;
	}
}

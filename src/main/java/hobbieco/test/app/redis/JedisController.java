package hobbieco.test.app.redis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.app.redis.service.JedisService;
import hobbieco.test.config.annotation.NoLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/redis/jedis")
public class JedisController {
	
	@Resource(name="jedisService")
	private JedisService service;
	
//	@NoLog
	@RequestMapping(value="/test",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> test(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("test", service.test(request));
		return result;
	}

	@RequestMapping(value="/test/pipeline",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> testPipeline(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("test", service.testPipeline());
		return result;
	}
	
	@RequestMapping(value="/del/all",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> delAll() {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "del");
		result.put("type", "all");
		result.put("rmk", "all");
		service.delAll();
		
		return result;
	}
	
	@RequestMapping(value="/set/string/all",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setStringAll() {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "set");
		result.put("type", "string");
		result.put("rmk", "all");
		service.setStringAll();
		
		return result;
	}
	
	@RequestMapping(value="/set/string/all/pipeline",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setStringAllPipeline() {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "set");
		result.put("type", "string");
		result.put("rmk", "all pipeline");
		service.setStringAllPipeline();
		
		return result;
	}
	
	@RequestMapping(value="/set/string",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setString(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "set");
		result.put("type", "string");
		result.put("rmk", "jedis pool");
		String number = request.getParameter("number");
		String loop = request.getParameter("loop");
		result.put("number", number);
		result.put("loop", loop);
		service.setString(Integer.parseInt(number),loop);
		
		return result;
	}
	
	@RequestMapping(value="/set/string/pipeline",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setStringPipeline(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "set");
		result.put("type", "string");
		result.put("rmk", "jedis pool pipeline");
		String number = request.getParameter("number");
		String loop = request.getParameter("loop");
		result.put("number", number);
		result.put("loop", loop);
		service.setStringPipeline(Integer.parseInt(number),loop);
		
		return result;
	}
}

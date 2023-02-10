package hobbieco.test.app.redis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.app.redis.service.LettuceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/redis/lettuce")
public class LettuceController {
	
	@Resource(name="lettuceService")
	private LettuceService service;

	@RequestMapping(value="/test",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> test() {
		Map<String,Object> result = new HashMap<>();
		result.put("key1", "lettuce value1");
		result.put("key2", "lettuce value2");
		return result;
	}
	
	@RequestMapping(value="/set/string",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> setString(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		result.put("cmd", "set");
		result.put("type", "string");
		result.put("rmk", "lettuce async");
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
		result.put("rmk", "lettuce async pipeline");
		String number = request.getParameter("number");
		String loop = request.getParameter("loop");
		result.put("number", number);
		result.put("loop", loop);
		service.setStringPipeline(Integer.parseInt(number),loop);
		
		return result;
	}
	
//	@RequestMapping(value="/set/string/pool",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
//	public Map<String,Object> setStringPool(HttpServletRequest request) {
//		Map<String,Object> result = new HashMap<>();
//		result.put("cmd", "set");
//		result.put("type", "string");
//		result.put("rmk", "lettuce pool async");
//		String number = request.getParameter("number");
//		String loop = request.getParameter("loop");
//		result.put("number", number);
//		result.put("loop", loop);
//		service.setStringPool(Integer.parseInt(number),loop);
//		
//		return result;
//	}
}

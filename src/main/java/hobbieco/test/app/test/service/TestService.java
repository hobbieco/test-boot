package hobbieco.test.app.test.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import hobbieco.test.app.test.mapper.TestMapperAppData;
import hobbieco.test.config.props.TestProps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestService {
	
	@Resource(name="testMapperAppData")
	private TestMapperAppData data;
	
	@Resource(name="testProps")
	private TestProps testProps;
	
	public Map<String,Object> test() {
		log.debug("call service method : test");
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("props", testProps.getTest());
		result.put("maria", data.testSelect());
		result.put("test", 123456);
		
		log.debug("test props get value : {}", result.get("props"));
		log.debug("oracle test : {}", result.get("oracle"));
		log.debug("maria test : {}", result.get("maria"));
		
		// test log level
		log.trace("test log - trace");
		log.debug("test log - debug");
		log.info("test log - info");
		log.warn("test log - warn");
		log.error("test log - error");
		
		return result;
	}
	
	public JSONObject testJson(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("test", "test value");
		JSONObject sub = new JSONObject();
		sub.put("subTest", "sub value");
		result.put("sub",sub);
		
		return result;
	}
}

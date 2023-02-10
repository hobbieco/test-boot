package hobbieco.test.app.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import hobbieco.test.app.api.mapper.ApiMapperAppData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {
	
	@Resource(name="apiMapperAppData")
	private ApiMapperAppData data;
	
	public Map<String,Object> test(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<>();
		
		log.debug("api service - test : start");
		
		result.put("test application database", data.testSelect());
		
		return result;
	}

}

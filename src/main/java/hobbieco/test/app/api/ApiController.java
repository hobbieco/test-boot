package hobbieco.test.app.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.app.api.service.ApiService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {
	
	@Resource(name="apiService")
	private ApiService service;
	
	@RequestMapping(value="/api/test", method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> test(HttpServletRequest request, HttpServletResponse response) {
		log.debug("call /api/test");
		Map<String,Object> result = service.test(request);
		return result;
	}

}

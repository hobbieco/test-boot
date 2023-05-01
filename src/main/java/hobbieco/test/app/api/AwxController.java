package hobbieco.test.app.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.app.api.service.AwxService;
import hobbieco.test.config.annotation.NoRequestLog;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/awx")
public class AwxController {

	@Resource(name="awxService")
	private AwxService service;
	
	@NoRequestLog
	@RequestMapping(value="/saveData", method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> test(HttpServletRequest request, HttpServletResponse response) {
		log.debug("call /api/awx/saveData");
		Map<String,Object> result = service.saveData(request);
		return result;
	}
}

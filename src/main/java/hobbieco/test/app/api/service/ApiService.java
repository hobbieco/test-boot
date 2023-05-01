package hobbieco.test.app.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hobbieco.test.app.api.mapper.ApiMapperApiData;
import hobbieco.test.common.JasyptUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {
	
	@Resource(name="apiMapperApiData")
	private ApiMapperApiData apiData;
	
	@Resource(name="apiServiceUtil")
	private ApiServiceUtil serviceUtil;
	
	@Resource(name="jasyptUtil")
	private JasyptUtil jasyptUtil;
	
	public Map<String,Object> test(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<>();
		
		log.debug("api service - test : start");
		
		result.put("test application database", apiData.testSelect());
		
		String test = "/api/test/{id}/replace";
		String replaceTest = test.replaceAll(StringUtils.join("\\{","id","\\}"), "9999");
		log.debug("{} ---->>>> {}", test, replaceTest);
		
		return result;
	}
	
	public Map<String,Object> jasypt(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		
		if (StringUtils.isNotBlank(request.getParameter("plantext"))) {
			result.put("plantext", request.getParameter("plantext"));
			result.put("crypttext", jasyptUtil.enc(request.getParameter("plantext")));
		} else if (StringUtils.isNotBlank(request.getParameter("crypttext"))) {
			result.put("plantext", jasyptUtil.dec(request.getParameter("crypttext")));
			result.put("crypttext", request.getParameter("crypttext"));
		} else {
			result.put("plantext", StringUtils.EMPTY);
			result.put("crypttext", StringUtils.EMPTY);
		}
		
		return result;
	}
	
	public Map<String,Object> lock(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		if (StringUtils.isNotBlank(request.getParameter("name")) && StringUtils.isNotBlank(request.getParameter("se"))) {
			if (serviceUtil.insertLock(request.getParameter("name"), request.getParameter("se"))) {
				result.put("result", "lock state changed");
			} else {
				result.put("result", "lock state unchanged");
			}
		} else {
			result.put("result", "parameter name and se required");
		}
		return result;
	}

}

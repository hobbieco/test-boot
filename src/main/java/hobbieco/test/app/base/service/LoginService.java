package hobbieco.test.app.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginService {
	
	public String testLogin(HttpServletRequest request, HttpServletResponse response) {
		String result = "/test/login";
		
		log.debug("login test start");
		
		
		return result;
	}

}

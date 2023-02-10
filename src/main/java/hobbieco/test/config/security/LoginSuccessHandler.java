package hobbieco.test.config.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hobbieco.test.common.CommonUtil;
import hobbieco.test.common.LogUtil;
import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource(name="commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name="logUtil")
	private LogUtil logUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		Map<String,Object> params = new HashMap<>();
		AuthUser customAuthUser = null;
		Object token = null;
		
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthUser) {
			customAuthUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} else {
			token = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		params.put(R.LOG.CLIENT_IP, commonUtil.getClientIP(request));
		params.put(R.LOG.SERVER_IP,  commonUtil.getServerIP());
		params.put(R.LOG.CONTROLLER_NM, "LoginSuccessLoggingAuthenticationSuccessHandler");
		params.put(R.LOG.METHOD_NM, "onAuthenticationSuccess");
		params.put(R.LOG.PARAMS, logUtil.getParams(request));
		params.put(R.LOG.LOG_TIME, LocalDateTime.now());
		params.put(R.LOG.REQUEST_URI, request.getRequestURI());
		params.put(R.LOG.HTTP_METHOD, request.getMethod());
		
		if (customAuthUser != null) {
			params.put(R.LOG.USER_INFO, customAuthUser.getUserId());
			// 사용자 마지막 로그 일시 업데이트
			// authUser 객체에 사용자 접속 IP 정보 넣는 등등

		} else if (token != null) {
			params.put(R.LOG.USER_INFO, token.toString());
		}
		
		log.info("#### Login : ", params);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	
}

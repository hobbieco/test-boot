package hobbieco.test.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import hobbieco.test.common.CommonUtil;
import hobbieco.test.common.LogUtil;
import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * Logout Log
 *
 */
@Slf4j
@Component
public class LogoutHandler implements LogoutSuccessHandler {

	@Resource(name="commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name="logUtil")
	private LogUtil logUtil;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof AuthUser) {
				AuthUser authUser = (AuthUser) authentication.getPrincipal();
				Map<String,Object> params = new HashMap<>();
				params.put(R.LOG.CLIENT_IP, commonUtil.getClientIP(request));
				params.put(R.LOG.SERVER_IP, commonUtil.getServerIP());
				params.put(R.LOG.CONTROLLER_NM, "LogoutHandler");
				params.put(R.LOG.METHOD_NM, "onLogoutSuccess");
				params.put(R.LOG.PARAMS, new JSONObject());
				params.put(R.LOG.REQUEST_URI, request.getRequestURI());
				params.put(R.LOG.HTTP_METHOD, request.getMethod());
				params.put(R.LOG.USER_INFO, authUser.getUserId());
				
				log.info("#### Logout : {}", params);
			}
		}
		
		response.sendRedirect("/login");
	}
}
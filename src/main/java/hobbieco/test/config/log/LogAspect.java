package hobbieco.test.config.log;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hobbieco.test.common.CommonUtil;
import hobbieco.test.common.LogUtil;
import hobbieco.test.common.R;
import hobbieco.test.config.security.AuthUser;
import lombok.extern.slf4j.Slf4j;

/**
 * HTTP 요청 로그 기록
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
	
	@Resource(name="commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name="logUtil")
	private LogUtil logUtil;
	
	/**
	 * HTTP 요청 로그 설정
	 */
	@Pointcut("execution(* hobbieco.test..*Controller.*(..)) && !@annotation(hobbieco.test.config.annotation.NoRequestLog)") // 이런 패턴이 실행될 경우 수행
    public void httpLogPointcut() {
		
    }
	
	/**
     * http request, response 로그
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("httpLogPointcut()")
    public Object httpLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); // request 정보를 가져온다.

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            AuthUser customAuthUser = null;
            Object token = null;

            Map<String, Object> params = new HashMap<>();

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
            
	            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthUser) {
	                customAuthUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	                //log.info(customAuthUser.getMemberModel().toString());
	            } else {
	                token = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	                log.info("########## LogAspect USER : {}", token.toString());
	            }
	
	            try {
	                params.put(R.LOG.CLIENT_IP, commonUtil.getClientIP(request));
	                params.put(R.LOG.SERVER_IP, commonUtil.getServerIP());
	                params.put(R.LOG.REQUEST_URI, request.getRequestURI());     // 호출URI
	                params.put(R.LOG.PARAMS, logUtil.getParams(request));               // 파라미터
	                if(customAuthUser != null) {
	                    params.put(R.LOG.USER_INFO, customAuthUser.getUserId());    // 로그인 사용자 정보
	                } else if(token != null){
	                    params.put(R.LOG.USER_INFO, token.toString());
	                }
	
	            } catch (Exception e) {
	                log.error("########## LogAspect error : {}", e);
	            }
	            //logService.insert(logUtil.convertLogData(params));
            }
            
            log.info("########## LogAspect  Request : {}", params);
            log.info("########## LogAspect Response : {}", result);
            return result;

        } catch (Throwable throwable) {
            throw throwable;
        }
    }
	
}

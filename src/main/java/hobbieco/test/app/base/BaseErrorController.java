package hobbieco.test.app.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BaseErrorController implements ErrorController {

	public String getErrorPath() {
		return R.ERROR_URL.ERROR;
	}

	/**
	 * 에러 처리 : ErrorPageController에 정의되지 않는 모든 에러
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.ERROR, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> error() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0010.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0010.getMessage());
		return rslt;
	}
	
	/**
	 * 404 : 요청 URL 없는 경우
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.NOT_FOUND, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> errorNotFound() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0020.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0020.getMessage());
		return rslt;
	}
	
	/**
	 * 405 : 요청 메소드 없는 경우
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.METHOD_NOT_ALLOWED, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> errorMethodNotAllowed() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0030.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0030.getMessage());
		return rslt;
	}
	
	/**
	 * 500 : 요청 처리중 오류 발생한 경우
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.INTERNAL_SERVER_ERROR, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> internalServerError() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0000.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0000.getMessage());
		return rslt;
	}
	
	/**
	 * 401 : 사용자 미인증인 경우
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.UNAUTHORIZED, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> unauthorized() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0040.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0040.getMessage());
		return rslt;
	}
	
	/**
	 * 403 : 접근 권한이 없는 경우
	 * @return Map<String,String>
	 */
	@RequestMapping(value=R.ERROR_URL.FORBIDDEN, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> forbidden() {
		Map<String,Object> rslt = new HashMap<>();
		rslt.put(R.RESULT_CODE, R.ResultCode.ERR0050.getCode());
		rslt.put(R.RESULT_MESSAGE, R.ResultCode.ERR0050.getMessage());
		return rslt;
	}
}

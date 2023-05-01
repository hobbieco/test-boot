package hobbieco.test.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class R {
	
	public static final String UNKNOWN = "unknown";
	public static final String RESULT_CODE = "resultCode";
	public static final String RESULT_MESSAGE = "resultMessage";
	public static final String READY = "ready";
	public static final String CURRENT = "current";
	public static final String DELETE = "delete";
	public static final String LOCK = "lock";
	public static final String UNLOCK = "unlock";
	public static final String PASSED = "passed";
	public static final String INTEGER = "integer";
	public static final String STRING = "string";
	public static final String API_AWX_SAVE_DATA = "apiAwxSaveData";

	/**
	 * Spring Profiles
	 */
	public interface Profile {
		public static final String PRODUCT = "prod";
		public static final String DEVELOP = "dev";
		public static final String LOCAL = "local";
	}
	
	// Header Name
	public static final List<String> IP_HEADER = new ArrayList<String>(
			Arrays.asList("x-forwarded-for", "x-real-ip", "proxy-client-ip", "wl-proxy-client-ip",
					"http_x_forwarded_for", "http_x_forwarded", "http_x_cluster_client_ip", "http_client_ip",
					"http_forwarded_for", "http_forwarded", "http_via", "remote_addr"));
	
	// 로그 항목
	public interface LOG {
		public static final String CLIENT_IP = "client_ip"; 		// 클라이언트 아이피
		public static final String SERVER_IP = "server_ip"; 		// 서버 IP
		public static final String CONTROLLER_NM = "controller_nm";	// 호출 컨트롤러(호출 클래스)
		public static final String METHOD_NM = "method_nm";			// 호출 메소드
		public static final String PARAMS = "msg";				// 파라미터
		public static final String REQUEST_URI = "rqst_uri";		// 호출 URL
		public static final String HTTP_METHOD = "rqst_method";		// HTTP 메소드
		public static final String USER_INFO = "user_info";			// 사용자 정보
		public static final String LOG_TIME = "log_time";			// 로그일시
	}
	
	/**
	 * Jasypt Configure
	 */
	public static enum JasyptConf {
		LOCAL("D9QkX8wx3yhCT6Wm","PBEWITHHMACSHA256ANDAES_256")
		,DEVELOP("D9QkX8wx3yhCT6Wm","PBEWITHSHA1ANDRC4_128")
		,PRODUCT("D9QkX8wx3yhCT6Wm","PBEWITHHMACSHA256ANDAES_256")
		;
		private String key;
		private String algorithm;
		JasyptConf(String key, String algorithm) {
			this.key = key;
			this.algorithm = algorithm;
		}
		public String getKey() {
			return key;
		}
		public String getAlgorithm() {
			return algorithm;
		}
	}
	
	public static interface ERROR_URL {
		public static final String ERROR = "/error";
		public static final String NOT_FOUND = "/error/notFound";
		public static final String METHOD_NOT_ALLOWED = "/error/methodNotAllowed";
		public static final String INTERNAL_SERVER_ERROR = "/error/internalServerError";
		public static final String UNAUTHORIZED = "/error/unauthorized";
		public static final String FORBIDDEN = "/error/forbidden";
	}
	
	public enum ResultCode {
		SUCCESS ("S0000","성공")//요청에 대한 처리 성공
		,ERR0000 ("E0000","요청을 처리 중 오류 발생")//요청을 처리 중 지정된 오류 외에 발생하는 오류
		,ERR0010 ("E0010","잘못된 요청")//지정된 오류 외에 발생하는 오류
		,ERR0020 ("E0020","존재하지 않는 요청")//요청한 URL 없음
		,ERR0030 ("E0030","처리할 수 없는 요청")//요청한 URL에 대한 HTTP 메소드 처리 없음
		,ERR0040 ("E0040","사용자 미인증 요청")//사용자 미인증인 경우
		,ERR0050 ("E0050","접근 권한없는 요청")//접근 권한이 없는 경우
		,ERR1000 ("E1000","인증 실패")//사용자 인증 오류
		,ERR2000 ("E2000","요청 파라메터 값 없음")//요청에 대한 필수 파라메터 값이 없는 경우
		;
		private String code;
		private String message;
		private String msg;
		ResultCode(String code, String message) {
			//에러 코드
			this.code = code;
			//에러 메시지 : 화면 출력용
			this.message = message;
			//에러 메시지 : 헤더 출력용
			this.msg = Base64.getEncoder().encodeToString(message.getBytes());
		}
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		public String getMsg() {
			return msg;
		}
	}
	
	public interface TESTER {
		public List<String> ADMIN = Arrays.asList("admin","manager");
		public List<String> USER = Arrays.asList("tester1","tester2");
	}
	
	// TCP Socket Server JSONbject Key
	public interface SocketServer {
		public static final String DATA = "DATA";
		public static final String RESULT = "RESULT";
		public static final String SUCCESS = "OK";
		public static final String ERROR = "ERROR";
		public static final String ERROR_REASON = "REASON";
		public static final String ERROR_MSG = "소켓 통신 처리 중 오류가 발생하였습니다.";
	}
	
	public enum Schedule {
		TEST_A("TEST-A","TEST","testJobDetailA","testJobTriggerB"),
		TEST_B("TEST-B","TEST","testJobDetailB","testJobTriggerB")
		;
		private String scheduleNm;
		private String scheduleCl;
		private String jobNm;
		private String triggerNm;
		Schedule(String scheduleNm, String scheduleCl, String jobNm, String triggerNm) {
			this.scheduleNm = scheduleNm;
			this.scheduleCl = scheduleCl;
			this.jobNm = jobNm;
			this.triggerNm = triggerNm;
		}
		public String getScheduleNm() {
			return scheduleNm;
		}
		public String getScheduleCl() {
			return scheduleCl;
		}
		public String getJobNm() {
			return jobNm;
		}
		public String getTriggerNm() {
			return triggerNm;
		}
	}

}

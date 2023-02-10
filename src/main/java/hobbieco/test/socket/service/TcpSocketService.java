package hobbieco.test.socket.service;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 */
@Slf4j
@Component
public class TcpSocketService {

	/**
	 * 기본 에러 메시지
	 */
	public void setErrorMsg(JSONObject data) {
		data.put(R.SocketServer.RESULT, R.SocketServer.ERROR);
		data.put(R.SocketServer.ERROR_REASON, R.SocketServer.ERROR_MSG);
		return;
	}
	
	
	/**
	 * 발신 메시지 작성
	 * @param data 수신 메시지 데이터
	 * @return
	 */
	public JSONObject setReport(JSONObject data) {
		JSONObject result = new JSONObject();
		
		if (StringUtils.isNotBlank(data.optString("수신 메시지 중 필요한 값 있는지 확인"))) {
			try {
				StringBuilder htmlData = new StringBuilder();
				htmlData.append("<p>TCP TEST</p>>");
				result.put(R.SocketServer.DATA, htmlData);
				
				result.put(R.SocketServer.RESULT, R.SocketServer.SUCCESS);
			} catch (Exception e) {
				log.warn("TCP Socket Sever 발신 메시지 처리 중 오류");
				log.warn(e.getMessage());
			}
		}
		
		if (StringUtils.isBlank(result.optString(R.SocketServer.RESULT))) {
			setErrorMsg(result);
		}
		return result;
	}

}

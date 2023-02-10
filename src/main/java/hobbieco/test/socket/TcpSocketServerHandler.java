package hobbieco.test.socket;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import hobbieco.test.common.ByteUtil;
import hobbieco.test.common.CommonUtil;
import hobbieco.test.config.props.TcpSocketProps;
import hobbieco.test.socket.service.TcpSocketService;
import lombok.extern.slf4j.Slf4j;

/**
 * TCP 소켓 수신, 송신 처리
 *
 */
@Slf4j
@Component
public class TcpSocketServerHandler {

	@Resource(name="tcpSocketProps")
	private TcpSocketProps props;
	
	@Resource(name="commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name="byteUtil")
	private ByteUtil byteUtil;
	
	@Resource(name="tcpSocketService")
	private TcpSocketService service;
	
	public byte[] handler(String message, MessageHeaders headers) {
		byte[] result = null;
		JSONObject recvData = null;
		JSONObject sendData = null;
		try {
			// 수신 메시지
			recvData = getRecvData(message);
			log.debug("socket server recvData : {}",recvData.toString());
		} catch (Exception e) {
			log.warn("TCP Socket Server 수신 처리 중 오류");
			log.warn(e.getMessage());
		}
		
		try {
			if (recvData != null) {
				// 응답 메시지
				sendData = service.setReport(recvData);
			} else {
				sendData = new JSONObject();
				service.setErrorMsg(sendData);
			}
			log.debug("socket server sendData : {}",sendData.toString());
			result = getSendBytes(sendData);
		} catch (Exception e) {
			log.warn("TCP Socket Server 송신 처리 중 오류");
			log.warn(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * TCP 소켓 수신 추출
	 */
	public JSONObject getRecvData(String recvMsg) throws Exception {
		JSONObject result = new JSONObject();
		// 수신 전문 byte 규격에 맞춰 수신 내용 추출
		
		for (TcpInput value : TcpInput.values()) {
			if (StringUtils.isEmpty(value.getSeparator())) {
				result.put(value.getKey(), new String(recvMsg.getBytes(props.getEncoding()), value.getOffset(), value.getLength(), props.getEncoding()).trim());
			} else {
				String[] strs = new String(recvMsg.getBytes(props.getEncoding()), value.getOffset(), value.getLength(), props.getEncoding()).trim().split(value.getSeparator());
				if (value.getKeys() == null) {
					JSONArray list = new JSONArray();
					for (String str : strs) {
						list.put(str);
					}
					result.put(value.getKey(), list);
				} else {
					JSONObject list = new JSONObject();
					for (int i=0; i < value.getKeys().size(); i++) {
						list.put(value.getKeys().get(i), strs[i]);
					}
					result.put(value.getKey(), list);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * TCP 소켓 응답 생성
	 * @return
	 * @throws Exception
	 */
	private byte[] getSendBytes(JSONObject params) throws Exception {
		byte[] byteArray = null;
		StringBuilder data = new StringBuilder();
		log.debug("######################## socket server getSendBytes()");
		// 응답 전문 byte 규격에 맞춰 송신 내용 생성
		
		// HEAD_LENGTH                 / LENGTH: 8    / OFFSET: 0    / 전문길이
		    // 송신 내용 생성 완료 후 처리 : byte 계산 시 미포함
		
		// HEAD_CODE                   / LENGTH: 2    / OFFSET: 8    / 구분코드
		data.append("10");
		// HEAD_GEN_YMD                / LENGTH: 6    / OFFSET: 10   / 생성일자
		data.append(commonUtil.getDateString("yyMMdd"));
		// RESULT                      / LENGTH: 10   / OFFSET: 16   / 처리 결과
		data.append(byteUtil.rpadByte(params.optString("result1"),10,StringUtils.SPACE,props.getEncoding()));

		// END                         / LENGTH: 3    / OFFSET: 26   / 종료
		data.append("EOF");

		// HEAD_LENGTH                 / LENGTH: 8    / OFFSET: 0    / 전문길이
		data.insert(0, byteUtil.lpadByte(String.valueOf(data.toString().getBytes(props.getEncoding()).length),8,"0",props.getEncoding()));

		log.debug("socket server sendMsg : {}",data.toString());
		byteArray = data.toString().getBytes(props.getEncoding());

		return byteArray;
	}

}

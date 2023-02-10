package hobbieco.test.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 */
@Slf4j
@Component
public class LogUtil {

	/**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     * @param request
     * @return
     */
    public JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            String value = request.getParameter(param);
            if (StringUtils.startsWith(value, "{") && StringUtils.endsWith(value, "}")) {
            	try {
            		JSONObject obj = new JSONObject(value);
            		jsonObject.put(replaceParam, obj);
            	} catch (Exception e) {
            		jsonObject.put(replaceParam, value);
            	}
            } else if (StringUtils.startsWith(value, "[") && StringUtils.endsWith(value, "]")) {
            	try {
            		JSONArray obj = new JSONArray(value);
            		jsonObject.put(replaceParam, obj);
            	} catch (Exception e) {
            		jsonObject.put(replaceParam, value);
            	}
            } else {
            	jsonObject.put(replaceParam, value);
            }
        }
        return jsonObject;
    }
}

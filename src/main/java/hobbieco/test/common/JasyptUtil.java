package hobbieco.test.common;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Jasypt String 암복호화 Util
 *
 */
@Slf4j
@Component
public class JasyptUtil {
	
	@Resource(name="jasyptStringEncryptor")
	private StringEncryptor stringEncryptor;

	/**
	 * 암호화
	 * @param str
	 * @return
	 */
	public String enc(String str) {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(str)) {
			result = stringEncryptor.encrypt(str);
		}
		return result;
	}
	
	/**
	 * 복호화
	 * @param str
	 * @return
	 */
	public String dec(String str) {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(str)) {
			result = stringEncryptor.decrypt(str);
		}
		return result;
	}
}

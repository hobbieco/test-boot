package hobbieco.test.app.api.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hobbieco.test.app.api.mapper.ApiMapperApiData;
import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author puttico
 *
 */
@Component
@Slf4j
public class ApiServiceUtil {

	@Resource(name="apiMapperApiData")
	private ApiMapperApiData apiData;
	
	@Transactional(value="transactionApiData",isolation=Isolation.READ_UNCOMMITTED,propagation=Propagation.REQUIRES_NEW)
	public boolean insertLock (String name, String se) {
		boolean result = false;
		if (StringUtils.isNotBlank(name)) {
			if (StringUtils.equals(se,R.LOCK)) {
				if (apiData.selectLock(name, R.LOCK) == 0) {
					apiData.insertLock(name, R.LOCK);
					result = true;
				}
			} else {
				apiData.insertLock(name, R.UNLOCK);
				result = true;
			}
		}
		return result;
	}

}

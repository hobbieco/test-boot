package hobbieco.test.app.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hobbieco.test.common.AwxUtil;
import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author puttico
 *
 */
@Service
@Slf4j
public class AwxService {

	@Resource(name="apiServiceUtil")
	private ApiServiceUtil apiServiceUtil;
	
	@Resource(name="awxServiceUtil")
	private AwxServiceUtil serviceUtil;
	
	public Map<String,Object> saveData(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		
		if (apiServiceUtil.insertLock(R.API_AWX_SAVE_DATA, R.LOCK)) {
			
			if (Boolean.valueOf(request.getParameter(AwxUtil.ORGANIZATION))) {
				result.put(AwxUtil.ORGANIZATION, StringUtils.EMPTY);
				result.put(AwxUtil.CREDENTIAL_TYPE, StringUtils.EMPTY);
				result.put(AwxUtil.CREDENTIAL, StringUtils.EMPTY);
				serviceUtil.saveOrganization(result);
			}
			
			if (Boolean.valueOf(request.getParameter(AwxUtil.INVENTORY))) {
				result.put(AwxUtil.INVENTORY, StringUtils.EMPTY);
				result.put(AwxUtil.INVENTORY_SOURCE, StringUtils.EMPTY);
				result.put(AwxUtil.GROUP, StringUtils.EMPTY);
				result.put(AwxUtil.HOST, StringUtils.EMPTY);
				result.put(AwxUtil.HOST_ALL_GROUP, StringUtils.EMPTY);
				serviceUtil.saveInventory(result);
			}
			
			if (Boolean.valueOf(request.getParameter(AwxUtil.PROJECT))) {
				result.put(AwxUtil.PROJECT, StringUtils.EMPTY);
				serviceUtil.saveProject(result);
			}
			
			if (Boolean.valueOf(request.getParameter(AwxUtil.JOB_TEMPLATE))) {
				result.put(AwxUtil.JOB_TEMPLATE, StringUtils.EMPTY);
				result.put(AwxUtil.WORKFLOW_JOB_TEMPLATE, StringUtils.EMPTY);
				serviceUtil.saveJobTemplate(result);
			}
			
			apiServiceUtil.insertLock(R.API_AWX_SAVE_DATA, R.UNLOCK);
		} else {
			result.put("lock", "awx save data locked");
		}
		
		return result;
	}
}

package hobbieco.test.app.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import hobbieco.test.app.api.mapper.AwxMapperApiData;
import hobbieco.test.common.AwxUtil;
import hobbieco.test.common.R;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author puttico
 *
 */
@Component
@Slf4j
public class AwxServiceUtil {

	@Resource(name="awxUtil")
	private AwxUtil awxUtil;
	
	@Resource(name="awxMapperApiData")
	private AwxMapperApiData awxData;
	
	/**
	 * save data
	 * @param list
	 */
	public void insertData(String targetData, List<Map<String,Object>> list) {
		for (List<Map<String,Object>> item : Lists.partition(list,100)) {
			awxData.insertData(targetData,item);
		}
	}
	
	@Transactional(value="transactionApiData",isolation=Isolation.READ_COMMITTED)
	public void saveOrganization (Map<String,Object> result) {
		String targetData = null;
		Map<String,String> pathParam = null;
		Map<String,String> reqParam = null;
		Map<String,List<String>> dataParam = null;
		List<Map<String,Object>> list = null;
		List<String> orderBy = null;
		String apiUrl = null;
		boolean loop = false;
		
		targetData = AwxUtil.ORGANIZATION;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/organizations/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.CREDENTIAL_TYPE;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/credential_types/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("kind",Arrays.asList(R.STRING,"kind"));
			dataParam.put("namespace",Arrays.asList(R.STRING,"namespace"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.CREDENTIAL;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/credentials/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("organization_id",Arrays.asList(R.INTEGER,"organization"));
			dataParam.put("credential_type_id",Arrays.asList(R.INTEGER,"credential_type"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		return;
	}
	
	@Transactional(value="transactionApiData",isolation=Isolation.READ_COMMITTED)
	public void saveInventory (Map<String,Object> result) {
		String targetData = null;
		Map<String,String> pathParam = null;
		Map<String,String> reqParam = null;
		Map<String,List<String>> dataParam = null;
		List<Map<String,Object>> list = null;
		List<String> orderBy = null;
		String apiUrl = null;
		boolean loop = false;
		
		targetData = AwxUtil.INVENTORY;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/inventories/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("organization_id",Arrays.asList(R.INTEGER,"organization"));
			dataParam.put("host_count",Arrays.asList(R.INTEGER,"total_hosts"));
			dataParam.put("group_count",Arrays.asList(R.INTEGER,"total_groups"));
			dataParam.put("inventory_source_count",Arrays.asList(R.INTEGER,"total_inventory_sources"));
			dataParam.put("variables",Arrays.asList(R.STRING,"variables"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.INVENTORY_SOURCE;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/inventory_sources/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			dataParam.put("source",Arrays.asList(R.STRING,"source"));
			dataParam.put("source_path",Arrays.asList(R.STRING,"source_path"));
			dataParam.put("source_project_id",Arrays.asList(R.INTEGER,"source_project"));
			dataParam.put("status",Arrays.asList(R.STRING,"status"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.GROUP;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/groups/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			dataParam.put("variables",Arrays.asList(R.STRING,"variables"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.HOST;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/hosts/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			dataParam.put("enabled",Arrays.asList(R.STRING,"enabled"));
			dataParam.put("variables",Arrays.asList(R.STRING,"variables"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.HOST_ALL_GROUP;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/hosts/{id}/all_groups/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("group_id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			int listSize = 0;
			orderBy = new ArrayList<>();
			orderBy.add("id");
			List<Map<String,String>> targetList = awxData.selectData(AwxUtil.HOST,StringUtils.joinWith(", ", orderBy));
			for (Map<String,String> map : targetList) {
				loop = true;
				pathParam = new HashMap<>();
				pathParam.put("id",map.get("id"));
				dataParam.put("host_id",Arrays.asList(StringUtils.EMPTY,map.get("id")));
				list = new ArrayList<>();
				while(loop) {
					JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
					list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
					if (StringUtils.isBlank(data.optString("next"))) {
						loop = false;
					} else {
						apiUrl = data.optString("next");
					}
				}
				if (list.size() > 0) {
					awxData.insertData(targetData,list);
					listSize += list.size();
				}
			}
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, listSize);
		} else {
			result.put(targetData, R.PASSED);
		}
		return;
	}
	
	@Transactional(value="transactionApiData",isolation=Isolation.READ_COMMITTED)
	public void saveProject (Map<String,Object> result) {
		String targetData = null;
		Map<String,String> pathParam = null;
		Map<String,String> reqParam = null;
		Map<String,List<String>> dataParam = null;
		List<Map<String,Object>> list = null;
		List<String> orderBy = null;
		String apiUrl = null;
		boolean loop = false;
		
		targetData = AwxUtil.PROJECT;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/projects/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("organization_id",Arrays.asList(R.INTEGER,"organization"));
			dataParam.put("local_path",Arrays.asList(R.STRING,"local_path"));
			dataParam.put("scm_type",Arrays.asList(R.STRING,"scm_type"));
			dataParam.put("scm_url",Arrays.asList(R.STRING,"scm_url"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		return;
	}
	
	@Transactional(value="transactionApiData",isolation=Isolation.READ_COMMITTED)
	public void saveJobTemplate (Map<String,Object> result) {
		String targetData = null;
		Map<String,String> pathParam = null;
		Map<String,String> reqParam = null;
		Map<String,List<String>> dataParam = null;
		List<Map<String,Object>> list = null;
		List<String> orderBy = null;
		String apiUrl = null;
		boolean loop = false;
		
		targetData = AwxUtil.JOB_TEMPLATE;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/job_templates/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("job_type",Arrays.asList(R.STRING,"job_type"));
			dataParam.put("organization_id",Arrays.asList(R.INTEGER,"organization"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			dataParam.put("project_id",Arrays.asList(R.INTEGER,"project"));
			dataParam.put("playbook",Arrays.asList(R.STRING,"playbook"));
			dataParam.put("verbosity",Arrays.asList(R.INTEGER,"verbosity"));
			dataParam.put("extra_vars",Arrays.asList(R.STRING,"extra_vars"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		
		targetData = AwxUtil.WORKFLOW_JOB_TEMPLATE;
		if (result.keySet().contains(targetData)) {
			apiUrl = "/api/v2/workflow_job_templates/";
			awxData.updateSe(targetData, R.READY, R.CURRENT);
			dataParam = new HashMap<>();
			dataParam.put("tableName",Arrays.asList(StringUtils.EMPTY,targetData));
			dataParam.put("id",Arrays.asList(R.INTEGER,"id"));
			dataParam.put("name",Arrays.asList(R.STRING,"name"));
			dataParam.put("description",Arrays.asList(R.STRING,"description"));
			dataParam.put("organization_id",Arrays.asList(R.INTEGER,"organization"));
			dataParam.put("inventory_id",Arrays.asList(R.INTEGER,"inventory"));
			dataParam.put("extra_vars",Arrays.asList(R.STRING,"extra_vars"));
			loop = true;
			list = new ArrayList<>();
			while(loop) {
				JSONObject data = new JSONObject(awxUtil.requestGet(apiUrl, pathParam, reqParam));
				list.addAll(awxUtil.makeList(targetData, dataParam, data.getJSONArray("results")));
				if (StringUtils.isBlank(data.optString("next"))) {
					loop = false;
				} else {
					apiUrl = data.optString("next");
				}
			}
			awxData.insertData(targetData,list);
			awxData.updateSe(targetData, R.DELETE, R.READY);
			result.put(targetData, list.size());
		} else {
			result.put(targetData, R.PASSED);
		}
		return;
	}
}

package hobbieco.test.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import hobbieco.test.config.props.AwxProps;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author puttico
 *
 */
@Component
@Slf4j
public class AwxUtil {
	
	public static final String ORGANIZATION = "organization";
	public static final String CREDENTIAL_TYPE = "credentialType";
	public static final String CREDENTIAL = "credential";
	public static final String INVENTORY = "inventory";
	public static final String INVENTORY_SOURCE = "inventorySource";
	public static final String GROUP = "group";
	public static final String HOST = "host";
	public static final String HOST_ALL_GROUP = "hostAllGroup";
	public static final String PROJECT = "project";
	public static final String PLAYBOOK = "playbook";
	public static final String JOB_TEMPLATE = "jobTemplate";
	public static final String WORKFLOW_JOB_TEMPLATE = "workflowJobTemplate";

	@Resource(name="awxProps")
	private AwxProps props;
	
	public List<Map<String,Object>> makeList(String targetData, Map<String,List<String>> dataParam, JSONArray json) {
		List<Map<String,Object>> result = new ArrayList<>();
		for (int i=0; i<json.length(); i++) {
			JSONObject data = json.getJSONObject(i);
			Map<String,Object> item = new HashMap<>();
			for (String key : dataParam.keySet()) {
				switch (dataParam.get(key).get(0)) {
				case R.INTEGER:
					item.put(key, data.optInt(dataParam.get(key).get(1),-1));
					break;
				case R.STRING:
					item.put(key, data.optString(dataParam.get(key).get(1),null));
					break;
				default:
					item.put(key, dataParam.get(key).get(1));
					break;
				}
			}
			result.add(item);
		}
		return result;
	}
	
	/**
	 * 사설 인증서 검증 우회 HttpClient 반환
	 * 사설 인증서인 경우 HttpClient client = HttpClients.createDefault();를 HttpClient client = getHttpClient();로 변경
	 */
	public CloseableHttpClient getHttpClient() throws Exception {
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(
				new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						return true;
					}
				}).build()
		);
		return builder.build();
	}
	
	/**
	 * AWX API 인증 Header
	 * @return
	 */
	public Header getCredential() {
		return new BasicHeader(
				HttpHeaders.AUTHORIZATION,
				StringUtils.join("Basic ",Base64.getEncoder().encodeToString(StringUtils.joinWith(":",props.getApiId(),props.getApiPwd()).getBytes()))
		);
	}
	
	/**
	 * make url
	 * @param url
	 * @param pathParam
	 * @param reqParam
	 * @return
	 */
	public String makeUrl (String url, Map<String,String> pathParam, Map<String,String> reqParam) {
		String result = StringUtils.join(props.getBaseUrl(),url);
		// url replace
		if (pathParam != null) {
			for (String key : pathParam.keySet()) {
				result = result.replaceAll(StringUtils.join("\\{",key,"\\}"), pathParam.get(key));
			}
		}
		
		// 요청 파라메터 처리
		if (reqParam != null) {
			try {
				// 요청 객체 생성
				URIBuilder builder = new URIBuilder(new URI(result));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : reqParam.keySet()) {
					params.add(new BasicNameValuePair(key, reqParam.get(key)));
				}
				// 요청 파라메터 추가
				builder.addParameters(params);
				result = builder.toString();
			} catch (URISyntaxException uriSyntaxException) {
				log.warn("AWX API GET - param 요청 중 URISyntexException 발생");
				log.warn(uriSyntaxException.getMessage());			
			}
		}
		return result;
	}
	
	/**
	 * AWS API - GET
	 * 
	 * @param String             url 요청 주소
	 * @param Map<String,String> param 파라메터
	 * @return String
	 */
	public String requestGet(String url, Map<String, String> pathParam, Map<String, String> reqParam) {
		String result = null;

		try {
			// 요청 객체 생성
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet(makeUrl(url,pathParam,reqParam));
			request.addHeader(getCredential());
			
			// 요청 후 응답 처리
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				result = String.valueOf(response.getStatusLine().getStatusCode());
			} else {
				result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			}
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			log.warn("AWX API GET - param 요청 중 UnsupportedEncodingException 발생");
			log.warn(unsupportedEncodingException.getMessage());
		} catch (IOException ioException) {
			log.warn("AWX API GET - param 요청 중 IOException 발생");
			log.warn(ioException.getMessage());
		} catch (ParseException parseException) {
			log.warn("AWX API GET - param 요청 중 ParseException 발생");
			log.warn(parseException.getMessage());
		} catch (Exception e) {
			log.warn("AWX API GET - param 요청 중 Exception 발생");
			log.warn(e.getMessage());
		}
		return result;
	}
	
	/**
	 * AWS API - POST
	 * 
	 * @param String             url 요청 주소
	 * @param Map<String,String> param 파라메터
	 * @return String
	 */
	public String requestPost(String url, Map<String, String> pathParam, Map<String, String> reqParam, String body) {
		String result = null;

		try {
			// 요청 객체 생성
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(makeUrl(url,pathParam,null));
			request.addHeader(getCredential());
			
			// 요청 파라메터 처리
			if (reqParam != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : reqParam.keySet()) {
					params.add(new BasicNameValuePair(key, reqParam.get(key)));
				}
				request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}
			
			// body String 처리
			if (StringUtils.isNotBlank(body)) {
				request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
				request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
			}

			// 요청 후 응답 처리
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				result = String.valueOf(response.getStatusLine().getStatusCode());
			} else {
				result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			}
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			log.warn("AWX API POST - param 요청 중 UnsupportedEncodingException 발생");
			log.warn(unsupportedEncodingException.getMessage());
		} catch (IOException ioException) {
			log.warn("AWX API POST - param 요청 중 IOException 발생");
			log.warn(ioException.getMessage());
		} catch (ParseException parseException) {
			log.warn("AWX API POST - param 요청 중 ParseException 발생");
			log.warn(parseException.getMessage());
		} catch (Exception e) {
			log.warn("AWX API POST - param 요청 중 Exception 발생");
			log.warn(e.getMessage());
		}
		return result;
	}
}

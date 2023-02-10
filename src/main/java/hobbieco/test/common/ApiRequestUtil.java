package hobbieco.test.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * API HTTP Request 요청 Util
 *
 */
@Slf4j
@Component
public class ApiRequestUtil {
	
	/**
	 * HTTP timeout 설정
	 */
	private RequestConfig getRequestConfig() {
		// timeout 기본 설정 30초
		return RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
				.setConnectionRequestTimeout(30000).build();
	}
	
	/**
	 * 사설 인증서 검증 우회 HttpClient 반환
	 * 사설 인증서인 경우 HttpClient client = HttpClients.createDefault();를 HttpClient client = getHttpClient();로 변경
	 */
	public CloseableHttpClient getHttpClient() throws Exception {
		return HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						return true;
					}
				}).build()).build();
	}

	/**
	 * GET방식의 url 및 파라미터 가공
	 * 
	 * @param String             url 요청 주소
	 * @param Map<String,String> param 파라메터
	 * @return String
	 */	
	public String buildGetUrl(String url, Map<String, String> param) {
		String urlWithParam = StringUtils.EMPTY;
		try {
			// 요청 객체 생성
			URIBuilder builder = new URIBuilder(new URI(url));
			// 요청 파라메터 처리
			if (param != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					params.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 요청 파라메터 추가
				builder.addParameters(params);
			}
			urlWithParam = builder.toString();
		} catch (URISyntaxException uriSyntaxException) {
			log.warn("외부 API GET - param 요청 중 URISyntexException 발생");
			log.warn(uriSyntaxException.getMessage());			
		}
		return urlWithParam;
	}	
	
	/**
	 * 외부 API 요청 - GET
	 * 
	 * @param String             url 요청 주소
	 * @param Map<String,String> param 파라메터
	 * @return String
	 */
	public String requestGet(String url, Map<String, String> param) {
		String result = StringUtils.EMPTY;
		
		try {
			//url 생성
			String urlWithParam = buildGetUrl(url, param);

			// 요청 객체 생성
			HttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(urlWithParam);
			// request config
			request.setConfig(getRequestConfig());

			// 요청 후 응답 변환
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			log.debug(result);
		} catch (IOException ioException) {
			log.warn("외부 API GET - param 요청 중 IOException 발생");
			log.warn(ioException.getMessage());
		} catch (ParseException parseException) {
			log.warn("외부 API GET - param 요청 중 ParseException 발생");
			log.warn(parseException.getMessage());
		} catch (Exception e) {
			log.warn("외부 API GET - param 요청 중 Exception 발생");
			log.warn(e.getMessage());
		}
		log.debug("APIREQUESTUTILL test: {}", result);
		return result;
	}

	/**
	 * 외부 API 요청 - POST with Parameter
	 * 
	 * @param String             url 요청 주소
	 * @param Map<String,String> param 파라메터
	 * @return String
	 */
	public String requestPost(String url, Map<String, String> param) {
		String result = null;

		try {
			// 요청 객체 생성
			HttpClient client = HttpClients.createDefault();
			HttpPost request = new HttpPost(url);
			// request config
			request.setConfig(getRequestConfig());

			// 요청 파라메터 처리
			if (param != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					params.add(new BasicNameValuePair(key, param.get(key)));
				}
				request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}

			// 요청 후 응답 처리
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			log.debug(result);
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			log.warn("외부 API POST - param 요청 중 UnsupportedEncodingException 발생");
			log.warn(unsupportedEncodingException.getMessage());
		} catch (IOException ioException) {
			log.warn("외부 API POST - param 요청 중 IOException 발생");
			log.warn(ioException.getMessage());
		} catch (ParseException parseException) {
			log.warn("외부 API POST - param 요청 중 ParseException 발생");
			log.warn(parseException.getMessage());
		} catch (Exception e) {
			log.warn("외부 API POST - param 요청 중 Exception 발생");
			log.warn(e.getMessage());
		}
		return result;
	}

	/**
	 * 외부 API 요청 - POST with body from JSONObject
	 * 
	 * @param String     url 요청 주소
	 * @param JSONObject body
	 * @return
	 */
	public String requestPost(String url, JSONObject body) {
		String result = null;
		if (body != null) {
			result = requestPost(url, body.toString());
		} else {
			result = requestPost(url, StringUtils.EMPTY);
		}
		return result;
	}

	/**
	 * 외부 API 요청 - POST with body from JSONArray
	 * 
	 * @param String    url 요청 주소
	 * @param JSONArray body
	 * @return
	 */
	public String requestPost(String url, JSONArray body) {
		String result = null;
		if (body != null) {
			result = requestPost(url, body.toString());
		} else {
			result = requestPost(url, StringUtils.EMPTY);
		}
		return result;
	}

	/**
	 * 외부 API 요청 - POST with body from String
	 * 
	 * @param String url 요청 주소
	 * @param String body
	 * @return
	 */
	public String requestPost(String url, String body) {
		String result = null;

		try {
			// 요청 객체 생성
			HttpClient client = HttpClients.createDefault();
			HttpPost request = new HttpPost(url);

			// body String 처리
			request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
			request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

			// 요청 후 응답 처리
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			log.debug("requestPost: " + result);

		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			log.warn("외부 API POST - body 요청 중 UnsupportedEncodingException 발생");
			log.warn(unsupportedEncodingException.getMessage());
		} catch (IOException ioException) {
			log.warn("외부 API POST - body 요청 중 IOException 발생");
			log.warn(ioException.getMessage());
		} catch (ParseException parseException) {
			log.warn("외부 API POST - body 요청 중 ParseException 발생");
			log.warn(parseException.getMessage());
		} catch (Exception e) {
			log.warn("외부 API POST - body 요청 중 Exception 발생");
			log.warn(e.getMessage());
		}
		return result;
	}
}

package com.joycity.joyclub.apifront.util;

import com.joycity.joyclub.apifront.exception.BusinessException;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.joycity.joyclub.apifront.constant.ResultCode.HTTPKIT_CONNECT_ERROR;

public class HttpKit {

	private static final Log logger = LogFactory.getLog(HttpKit.class);
	
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static String post(String url, String s) throws IOException,
			ExecutionException, InterruptedException {
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		builder.setBody(s);
		Future<Response> f = builder.execute();
		String body = f.get().getResponseBody(DEFAULT_CHARSET);
		http.close();
		return body;
	}

	public static String get(String url, Map<String, String> params,
			Map<String, String> headers) throws IOException,
			ExecutionException, InterruptedException {
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		if (params != null && !params.isEmpty()) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				builder.addQueryParam(key, params.get(key));
			}
		}

		if (headers != null && !headers.isEmpty()) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				builder.addHeader(key, params.get(key));
			}
		}
		Future<Response> f = builder.execute();
		String body = f.get().getResponseBody(DEFAULT_CHARSET);
		http.close();
		return body;
	}


	public static String post(String uri, Map<String, String> param) {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = createSSLClientDefault();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			// 发起HTTP的POST请求
			httpPost = new HttpPost(uri);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (String key : param.keySet()) {
				paramList.add(new BasicNameValuePair(key, param.get(key)));
			}
			// UTF8+URL编码
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
			httpPost.setConfig(RequestConfig.custom().setConnectTimeout(30000)
					.setSocketTimeout(30000).build());
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode)  {
				// 如果响应码是200
				return EntityUtils.toString(entity);
			} else {
				logger.error("connection error.... .... ......." + statusCode);
				throw new BusinessException(HTTPKIT_CONNECT_ERROR);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CloseableHttpClient createSSLClientDefault() throws Exception {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
				new AllTrustStrategy()).build();
		SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(
				sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslSf).build();
	}

	// 加载证书
	private static class AllTrustStrategy implements TrustStrategy {

		public boolean isTrusted(X509Certificate[] x509Certificates, String s)
				throws CertificateException {
			return true;
		}
	}

}

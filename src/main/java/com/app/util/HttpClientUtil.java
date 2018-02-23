package com.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	private static int connectTimeout = 5000;//链接超时时间
	private static int socketTimeout = 3000;//读取超时时间 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
	private static int connectionRequestTimeout = 2000;//设置从connect Manager获取Connection 超时时间
	
	/**
	 * http POST发送方式
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url,Map<String, Object> params){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = setRequestConfig();
		httpPost.setConfig(requestConfig);
		List<NameValuePair> list = getNameValuePair(params);
		//将参数拼装到http链接中
		EntityBuilder entityBuilder = EntityBuilder.create();
		entityBuilder.setParameters(list);
		httpPost.setEntity(entityBuilder.build());
		
		CloseableHttpResponse response =  null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(entity, "UTF-8");//将httpEntity转为字符窜
			}
			EntityUtils.consume(entity);// 清除返回的httpEntity
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
			}

		}
		return result;
	}
	
	/**
	 * 设置链接时间参数
	 * @return
	 */
	public static RequestConfig setRequestConfig(){
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).
				setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
		return requestConfig;
	}
	
	/**
	 * 拼装HTTPClient所需参数
	 * @param params
	 * @return
	 */
	public static List<NameValuePair> getNameValuePair(Map<String, Object> params){
		List<NameValuePair> list = new ArrayList<NameValuePair>(params.size());
		for(Map.Entry<String, Object> entry : params.entrySet()){
			NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			list.add(nameValuePair);
		}
		return list;
	}
}

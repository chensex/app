package com.app.util;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		String url="http://localhost:8080/app/app/system/login";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", "admin");
		params.put("password", "111111");
		String result = HttpClientUtil.httpPost(url, params);
		System.out.println(result);
	}

}

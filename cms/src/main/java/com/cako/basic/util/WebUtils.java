package com.cako.basic.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRequestToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					System.out.println("参数：" + paramName + "=" + paramValue);
					String[] str = paramName.split("_");
					if (str.length > 1) {
						map.put(paramName, paramValue);
					}
				}
			}
		}
		return map;
	}
}

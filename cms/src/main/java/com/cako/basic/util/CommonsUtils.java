package com.cako.basic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonsUtils {

	final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");
	
	public static String parseSuffix(String url) {
		Matcher matcher = pattern.matcher(url);
		String[] spUrl = url.toString().split("/");
		int len = spUrl.length;
		String endUrl = spUrl[len - 1];

		if (matcher.find()) {
			String[] spEndUrl = endUrl.split("\\?");
			return spEndUrl[0].split("\\.")[1];
		}
		return endUrl.split("\\.")[1];
	}

}

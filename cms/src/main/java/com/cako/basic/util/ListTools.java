package com.cako.basic.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListTools {

	public static List<String> toArrayList(String str){
		List<String> array = new ArrayList<String>();
		if (StringUtils.isNotEmpty(str)) {
			String[] strs = str.split(",");
			for (String string : strs) {
				array.add(string);
			}
		}
		return array;
	}
}

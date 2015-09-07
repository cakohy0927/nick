package com.cako.basic.util;

/**
 * 常量类
 * 
 * @author nick
 *
 */
public class SysContent {

	/**
	 * 新闻的发布状态
	 * @author nick
	 *
	 */
	public static class DeployStatus{
		
		/**
		 * 已发布
		 */
		public static final boolean DEPLOY = true;
		
		/**
		 * 未发布
		 */
		public static final boolean NODEPLOY = false;
		
		private static final String DEPLOY_NAME = "已发布";
		private static final String NODEPLOYNAME = "未发布";
		
		/**
		 * 获得发布状态的名称
		 * @param code
		 * @return
		 */
		public static String getName(boolean code) {
			String name = "";
			Integer flag = 0;
			if (code) {
				flag = 1;
			}
			switch (flag) {
			case 1:
				name = DEPLOY_NAME;
				break;
			case 0:
				name = NODEPLOYNAME;
				break;
			}
			return name;
		}
	}
	
	/**
	 * 是否可用状态
	 * 
	 * @author nick
	 *
	 */
	public static class IsDisable {

		public static final String NODISABLE = "1";
		public static final String DISABLE = "0";
		private static final String NODISABLE_NAME = "可用";
		private static final String DISABLE_NAME = "不可用";
		public static String getName(String code) {
			String name = "";
			switch (Integer.parseInt(code)) {
			case 1:
				name = NODISABLE_NAME;
				break;

			case 0:
				name = DISABLE_NAME;
				break;
			}
			return name;
		}
	}
}

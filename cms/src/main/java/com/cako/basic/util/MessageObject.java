package com.cako.basic.util;

public class MessageObject {

	private Integer resposeCode;

	private String result;

	private String currentPage;

	/**
	 * 总记录数
	 */
	private Integer totalNumber;

	/**
	 * 自定义 信息
	 */
	private String inforamation;

	private Object object;

	public static interface Result {
		public static final String SUCCESS = "success";
		public static final String FAILIAR = "failiar";
	}

	public MessageObject(String inforamation) {
		this.inforamation = inforamation;
	}

	public MessageObject() {
	}

	public static interface ResposeCode {
		/**
		 * 请求成功代码
		 */
		public static final Integer code_200 = 200;
		/**
		 * 请求失败代码
		 */
		public static final Integer code_404 = 404;
		/**
		 * 请求错误代码
		 */
		public static final Integer code_403 = 403;
		
		public static final Integer code_100 = 100;
	}

	public Integer getResposeCode() {
		return resposeCode;
	}

	public void setResposeCode(Integer resposeCode) {
		this.resposeCode = resposeCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getInforamation() {
		return inforamation;
	}

	public void setInforamation(String inforamation) {
		this.inforamation = inforamation;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}
}

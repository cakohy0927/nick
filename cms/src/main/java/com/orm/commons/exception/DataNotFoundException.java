package com.orm.commons.exception;


/**
 * 自定义一异常，用于封装数据查找所发生的异常
 * @author Eleven
 * @创建日期：2012-6-11
 * @版本1.0
 *
 */
public class DataNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1673497841999901447L;

	public DataNotFoundException(){
		
	}
	
	public DataNotFoundException(String message){
		super(message);
	}
	
	public DataNotFoundException(String message,
            Throwable cause){
		super(message,cause);
	}

}

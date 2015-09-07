package com.orm.commons.exception;

/**
 * 自定义数据更新处理异常
 * 用于封装DAO对于数据操作所引起的异常信息
 * @author Eleven
 * @创建日期：2012-6-11
 * @版本1.0
 *
 */
public class DataUpdateException extends Exception {
     
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8085284598615734063L;

	public DataUpdateException(){
		
	}
	
	public DataUpdateException(String message){
		super(message);
	}
	
	public DataUpdateException(String message,
            Throwable cause){
		super(message,cause);
	}
	
}

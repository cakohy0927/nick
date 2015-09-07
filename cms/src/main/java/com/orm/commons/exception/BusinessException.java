package com.orm.commons.exception;

/**
 * Service层自定义异常
 * 封装所有的业务异常
 * 
 * @author wolf
 *
 */
public class BusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6517370179270162875L;

	public BusinessException(){
		
	}
	
	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(String message,
            Throwable cause){
		super(message,cause);
	}

}

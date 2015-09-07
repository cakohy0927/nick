package com.orm.commons.exception;

/**
 * Created by huangyuan on 2015/2/10.
 */
public class LoginException extends Exception {

    private static final long serialVersionUID = -6517329837270162875L;

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

}

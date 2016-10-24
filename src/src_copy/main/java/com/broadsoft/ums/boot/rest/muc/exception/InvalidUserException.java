package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if user's is invalid
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class InvalidUserException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = -5281557800737653057L;

	/** Error code */
	public static final String CODE = "1000006";
	
	/** Error message */
	private static final String MESSAGE = "User(s) is(are) not valid";
	
	/**
	 * Default constructor
	 */
	public InvalidUserException() {
		super(MESSAGE, CODE);
	}
	
}

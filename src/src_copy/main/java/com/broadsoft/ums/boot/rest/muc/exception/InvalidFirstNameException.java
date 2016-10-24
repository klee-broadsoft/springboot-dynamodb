package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if user's first name is invalid
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class InvalidFirstNameException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = 6265823797723097988L;

	/** Error code */
	public static final String CODE = "1000004";
	
	/** Error message */
	private static final String MESSAGE = "Empty First Name"; 

	/**
	 * Default constructor
	 */
	public InvalidFirstNameException() {
		super(MESSAGE, CODE);
	}
}

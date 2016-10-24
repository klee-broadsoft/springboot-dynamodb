package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class InvalidOrMissingFieldException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 5169313815011003658L;
	
	/** Error code */
	public static final String CODE = "1000003";
	
	/** Error message */
	private static final String MESSAGE = "Is Invalid or Missing!";
	
	/**
	 * Default constructor
	 */
	public InvalidOrMissingFieldException() {
		super(MESSAGE, CODE);
	}
}

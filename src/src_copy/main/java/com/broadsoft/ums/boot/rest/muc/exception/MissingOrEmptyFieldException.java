package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class MissingOrEmptyFieldException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 5952563015122676300L;
	
	/** Error code */
	public static final String CODE = "1000002";
	
	/** Error message */
	private static final String MESSAGE = "Is Missing or empty field";
	
	/**
	 * Default constructor
	 */
	public MissingOrEmptyFieldException() {
		super(MESSAGE, CODE);
	}
}

package com.broadsoft.ums.boot.rest.presence.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception will be thrown if show is invalid.
 * 
 * @author rdokov
 * 
 */
public class InvalidShowException extends UmsException{

	/** Serial Id*/
	private static final long serialVersionUID = 1L;
	
	/** Error code */
	public static final String CODE = "0200001";
	
	/** Error message */
	private static final String MESSAGE = "Invalid Show!"; 

	/** Default constructor */
	public InvalidShowException() {
		super(MESSAGE, CODE);
	}
}
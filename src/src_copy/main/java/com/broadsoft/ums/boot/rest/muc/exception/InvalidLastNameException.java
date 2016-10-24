package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if user's last name is invalid
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class InvalidLastNameException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = -7566626892957666783L;
	
	/** Error code */
	public static final String CODE = "1000005";
	
	/** Error message */
	private static final String MESSAGE = "Empty Last Name";

	/**
	 * Default constructor
	 */
	public InvalidLastNameException() {
		super(MESSAGE, CODE);
	}
	
}

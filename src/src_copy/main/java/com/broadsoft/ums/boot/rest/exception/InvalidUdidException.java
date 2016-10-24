package com.broadsoft.ums.boot.rest.exception;

/**
 * This exception must be thrown if udid is invalid
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class InvalidUdidException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = -8928123586690669422L;
	
	/** Error code */
	public static final String CODE = "1000003";
	
	/** Error message */
	private static final String MESSAGE = "Invalid UDID/Resource of the user"; 

	/** Default constructor */
	public InvalidUdidException(){
		super(MESSAGE , CODE);
	}
}
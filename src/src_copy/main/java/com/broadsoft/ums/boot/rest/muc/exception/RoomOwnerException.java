package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if user is not an owner of the room
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class RoomOwnerException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = -5732992461800531200L;
	
	/** Error code */
	public static final String CODE = "1000303";
	
	/** Error message */
	private static final String MESSAGE = "Not owner"; 

	/**
	 * Default constructor
	 */
	public RoomOwnerException() {
		super(MESSAGE, CODE);
	}
	
}

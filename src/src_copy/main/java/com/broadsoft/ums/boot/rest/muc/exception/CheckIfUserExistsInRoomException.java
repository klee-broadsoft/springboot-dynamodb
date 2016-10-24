package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class CheckIfUserExistsInRoomException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 2516881006170713507L;

	/** Error code */
	public static final String CODE = "0000801";
	
	/** Error message */
	private static final String MESSAGE = "Check if user exists in room request processed"; 

	/**
	 * Default constructor
	 */
	public CheckIfUserExistsInRoomException() {
		super(MESSAGE, CODE);
	}
}

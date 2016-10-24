package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if room does not exist
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class RoomDoesNotExistException extends UmsException{

	/** Serial Id*/
	private static final long serialVersionUID = 838280345875132824L;
	
	/** Error code */
	public static final String CODE = "1000002";
	
	/** Error message */
	private static final String MESSAGE = "Room does not exist"; 

	/**
	 * Default constructor
	 */
	public RoomDoesNotExistException(){
		super(MESSAGE , CODE);
	}

}

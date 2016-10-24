package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if room already exist
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class RoomAlreadyExistsException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = -5509034158006923394L;

	/** Error code */
	public static final String CODE = "1000603";
	
	/** Error message */
	private static final String MESSAGE = "Room already exists";
	
	/**
	 * Default constructor
	 */
	public RoomAlreadyExistsException() {
		super(MESSAGE, CODE);
	}
}
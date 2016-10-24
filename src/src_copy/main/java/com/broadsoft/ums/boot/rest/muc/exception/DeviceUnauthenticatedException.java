package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class DeviceUnauthenticatedException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = -8066111084067125726L;

	/** Error code */
	public static final String CODE = "2000004";
	
	/** Error message */
	private static final String MESSAGE = "Error, Request made by un-authenticated user"; 

	/**
	 * Default constructor
	 */
	public DeviceUnauthenticatedException() {
		super(MESSAGE, CODE);
	}
}

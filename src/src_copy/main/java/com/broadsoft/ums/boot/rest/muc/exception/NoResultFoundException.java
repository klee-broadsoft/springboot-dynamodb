package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class NoResultFoundException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = -1227910533680230025L;
	
	/** Error code */
	public static final String CODE = "0000005";
	
	/** Error message */
	private static final String MESSAGE = "No Result Found"; 

	/**
	 * Default constructor
	 */
	public NoResultFoundException() {
		super(MESSAGE, CODE);
	}
}

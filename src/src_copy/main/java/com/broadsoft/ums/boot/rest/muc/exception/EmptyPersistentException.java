package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class EmptyPersistentException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 3653750540167872333L;
	
	/** Error code */
	public static final String CODE = "1000604";
	
	/** Error message */
	private static final String MESSAGE = "Empty Persistent";

	/**
	 * Default constructor
	 */
	public EmptyPersistentException(){
		super(MESSAGE, CODE);
	}
}
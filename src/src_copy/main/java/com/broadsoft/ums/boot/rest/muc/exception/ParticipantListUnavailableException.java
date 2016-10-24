package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

public class ParticipantListUnavailableException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 7829179940970496096L;

	/** Error code */
	public static final String CODE = "1000601";
	
	/** Error message */
	private static final String MESSAGE = "Participant list unavailable"; 

	/**
	 * Default constructor
	 */
	public ParticipantListUnavailableException() {
		super(MESSAGE, CODE);
	}
}

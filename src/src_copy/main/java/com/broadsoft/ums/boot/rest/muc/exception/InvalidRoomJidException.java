package com.broadsoft.ums.boot.rest.muc.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception must be thrown if room's jid is invalid
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class InvalidRoomJidException extends UmsException {
	
	/** Serial Id*/
	private static final long serialVersionUID = 7579544006547763090L;
	
	/** Error code */
	public static final String CODE = "1000001";
	
	/** Error message */
	private static final String MESSAGE = "Invalid Room JID"; 

	/**
	 * Default constructor
	 */
	public InvalidRoomJidException(){
		super(MESSAGE , CODE);
	}
}

package com.broadsoft.ums.boot.rest.presence.exception;

import com.broadsoft.ums.boot.rest.exception.UmsException;

/**
 * This exception will be thrown if FreeText is empty.
 * 
 * @author rdokov
 *
 */
public class EmptyFreeTextException extends UmsException {

	/** Serial Id*/
	private static final long serialVersionUID = 2700768176828888717L;

	/** Error code */
	public static final String CODE = "9200005";

	/** Error message */
	private static final String message = "Empty FreeText!";
	
	/** Default constructor */
	public EmptyFreeTextException() {
		super(message, CODE);
	}
}
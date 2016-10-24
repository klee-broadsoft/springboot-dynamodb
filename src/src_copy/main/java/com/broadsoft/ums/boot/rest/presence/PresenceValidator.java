package com.broadsoft.ums.boot.rest.presence;

import com.broadsoft.ums.boot.rest.BaseValidator;
import com.broadsoft.ums.boot.rest.exception.UmsException;
import com.broadsoft.ums.boot.rest.presence.exception.EmptyFreeTextException;
import com.broadsoft.ums.boot.rest.presence.exception.InvalidShowException;

/**
 * Validator for input data
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
public class PresenceValidator extends BaseValidator{
	
	/**
	 * Check for empty freeText.
	 * @param freeText
	 * @throws UmsException
	 */
	public static void validateFreeText(String freeText) throws UmsException{
		if(null == freeText || freeText.trim().length() == 0) throw new EmptyFreeTextException();
		if(freeText.indexOf(EmptyFreeTextException.CODE) > - 1) throw new EmptyFreeTextException();
	}

	/**
	 * Check if show is invalid.
	 * @param show
	 * @throws UmsException
	 */
	public static void validateShow(String show) throws UmsException {
		if(null == show || show.trim().length() == 0) throw new InvalidShowException();
		if(!"away".equalsIgnoreCase(show) || "busy".equalsIgnoreCase(show)) throw new InvalidShowException();
//		if(show.indexOf(InvalidShowException.CODE) > -1) throw new InvalidShowException(); //actually, it can never occurs, because of the upper check
	}
}
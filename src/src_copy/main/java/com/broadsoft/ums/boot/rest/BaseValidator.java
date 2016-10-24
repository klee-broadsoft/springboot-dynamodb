package com.broadsoft.ums.boot.rest;

import com.broadsoft.ums.boot.rest.exception.InvalidUdidException;
import com.broadsoft.ums.boot.rest.exception.UmsException;

public abstract class BaseValidator {

	/**
	 * Check if JID is invalid.
	 * 
	 * @param udid
	 * 			- udid
	 * @throws UmsException
	 * 			- if udid is invalid
	 */
	public static void validateUDID(String udid) throws UmsException {
		if(udid.indexOf(InvalidUdidException.CODE) > -1) throw new InvalidUdidException();
	}
}
package com.broadsoft.ums.boot.rest.exception;

public class UmsException extends Exception {

	/** Serial Id*/
	private static final long serialVersionUID = -1695340610713317597L;

	private String code;

	public UmsException() {

	}
	
	public UmsException(String message, String code){
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}

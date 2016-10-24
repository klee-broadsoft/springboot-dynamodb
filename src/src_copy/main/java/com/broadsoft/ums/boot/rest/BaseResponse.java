package com.broadsoft.ums.boot.rest;

import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * Base response class that contains a {@link Status}
 * 
 * * <pre>
 * {@code
 * {
 *   "status": {
 *        "code": "string",
 *        "message": "string",
 *        "type": "SUCCESS"
 *   }
 * }
 * 
 * </pre>
 * 
 * @author rdokov
 *
 */
public abstract class BaseResponse {
	
	private Status status;
	
	/**
	 * Default constructor
	 */
	public BaseResponse() {
		
	}
	
	public BaseResponse(String code, ResponseTypes type, String message) {
		status = new Status(code, type, message);
	}
	
	public Status getStatus() {
		return status;
	}
	
}

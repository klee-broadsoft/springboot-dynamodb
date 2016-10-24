package com.broadsoft.ums.boot.rest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadsoft.ums.boot.rest.Status.ResponseTypes;
import com.broadsoft.ums.boot.rest.exception.UmsException;

public abstract class UmsController {

	/**
	 * Exception handler method is called by Spring when {@link UmsException} is thrown
	 * 
	 * It returns {@link Status} build from {@link UmsException}
	 * 
	 * @param exception handed
	 * 
	 * @return
	 * 		Status as JSON
	 */
	@ExceptionHandler({UmsException.class})
	public @ResponseBody Status error(UmsException e) {
		Status status = new Status();
		status.setCode(e.getCode());
		status.setMessage(e.getMessage());
		status.setType(ResponseTypes.ERROR);
		return status;
	}
}

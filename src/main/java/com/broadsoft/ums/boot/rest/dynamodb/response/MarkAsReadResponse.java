package com.broadsoft.ums.boot.rest.dynamodb.response;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class MarkAsReadResponse extends BaseResponse {
	private static final String MARKASREAD_SUCCESSFUL = "0000100";

	private static final String MARKASREAD_REQUEST_PROCESSED = "MarkAsRead request processed."; 
	
	public MarkAsReadResponse(){
		super(MARKASREAD_SUCCESSFUL, ResponseTypes.SUCCESS, MARKASREAD_REQUEST_PROCESSED);
	}
}

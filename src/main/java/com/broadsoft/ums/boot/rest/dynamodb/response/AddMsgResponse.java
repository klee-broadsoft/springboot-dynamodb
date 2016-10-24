package com.broadsoft.ums.boot.rest.dynamodb.response;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class AddMsgResponse  extends BaseResponse {
	private static final String ADD_MESSAGE_SUCCESSFUL = "0000100";

	private static final String ADD_MESSAGE_REQUEST_PROCESSED = "AddMsg request processed."; 
	
	public AddMsgResponse(){
		super(ADD_MESSAGE_SUCCESSFUL, ResponseTypes.SUCCESS, ADD_MESSAGE_REQUEST_PROCESSED);
	}
}

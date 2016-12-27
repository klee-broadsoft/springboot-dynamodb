package com.broadsoft.ums.boot.rest.dynamodb.response;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class AddMsgResponse  extends BaseResponse {
	private static final String ADD_MESSAGE_SUCCESSFUL = "0000100";

	private static final String ADD_MESSAGE_REQUEST_PROCESSED = "AddMsg request processed."; 
	PutItemResult putItemResult = null;
	
	public PutItemResult getPutItemResult() {
		return putItemResult;
	}

	public void setPutItemResult(PutItemResult putItemResult) {
		this.putItemResult = putItemResult;
	}

	public AddMsgResponse(){
		super(ADD_MESSAGE_SUCCESSFUL, ResponseTypes.SUCCESS, ADD_MESSAGE_REQUEST_PROCESSED);
	}
	
	public AddMsgResponse(PutItemResult putItemResult){
		super(ADD_MESSAGE_SUCCESSFUL, ResponseTypes.SUCCESS, ADD_MESSAGE_REQUEST_PROCESSED);
		setPutItemResult(putItemResult);	
	}

}

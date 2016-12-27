package com.broadsoft.ums.boot.rest.dynamodb.response;

import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class MarkAsReadResponse extends BaseResponse {
	private static final String MARKASREAD_SUCCESSFUL = "0000100";

	private static final String MARKASREAD_REQUEST_PROCESSED = "MarkAsRead request processed."; 
	
	private UpdateItemResult updateRes = null;

	public MarkAsReadResponse(UpdateItemResult updateRes){
		super(MARKASREAD_SUCCESSFUL, ResponseTypes.SUCCESS, MARKASREAD_REQUEST_PROCESSED);
		this.setUpdateRes(updateRes);
	}

	public UpdateItemResult getUpdateRes() {
		return updateRes;
	}

	public void setUpdateRes(UpdateItemResult updateRes) {
		this.updateRes = updateRes;
	}
}

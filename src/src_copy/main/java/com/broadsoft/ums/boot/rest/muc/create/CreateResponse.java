package com.broadsoft.ums.boot.rest.muc.create;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class CreateResponse extends BaseResponse {
	
	private static final String CREATE_ROOM_SUCCESSFUL = "0000601";
	
	private static final String ROOM_CREATED_PROCESSED = "Room creation request processed";
	
	public CreateResponse() {
		super(CREATE_ROOM_SUCCESSFUL, ResponseTypes.SUCCESS, ROOM_CREATED_PROCESSED);
	}
}
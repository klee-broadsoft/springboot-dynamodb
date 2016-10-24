package com.broadsoft.ums.boot.rest.muc.kick;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class KickResponse extends BaseResponse {
	
	private static final String KICK_USER_SUCCESSFUL = "0000501";
	
	private static final String KICK_REQUEST_PROCESSED = "Room kick request processed";

	public KickResponse() {
		super(KICK_USER_SUCCESSFUL, ResponseTypes.SUCCESS, KICK_REQUEST_PROCESSED);
	}
}
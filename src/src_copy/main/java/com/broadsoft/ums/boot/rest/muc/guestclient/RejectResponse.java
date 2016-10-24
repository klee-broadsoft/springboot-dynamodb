package com.broadsoft.ums.boot.rest.muc.guestclient;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class RejectResponse extends BaseResponse {
	
	private static final String REJECT_GUEST_CLIENT_SUCCESSFUL = "0000801";
	
	private static final String REJECT_GUEST_CLIENT_PROCESSED = "Guest client user reject request processed";
	
	public RejectResponse() {
		super(REJECT_GUEST_CLIENT_SUCCESSFUL, ResponseTypes.SUCCESS, REJECT_GUEST_CLIENT_PROCESSED);
	}
}

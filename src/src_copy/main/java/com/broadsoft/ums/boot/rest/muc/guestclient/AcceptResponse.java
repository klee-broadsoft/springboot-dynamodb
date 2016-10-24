package com.broadsoft.ums.boot.rest.muc.guestclient;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class AcceptResponse extends BaseResponse {
	
	private static final String ACCEPT_GUEST_CLIENT_SUCCESSFUL = "0000701";
	
	private static final String ACCEPT_GUEST_CLIENT_PROCESSED = "Guest client user accept request processed";
	
	public AcceptResponse() {
		super(ACCEPT_GUEST_CLIENT_SUCCESSFUL, ResponseTypes.SUCCESS, ACCEPT_GUEST_CLIENT_PROCESSED);
	}
}

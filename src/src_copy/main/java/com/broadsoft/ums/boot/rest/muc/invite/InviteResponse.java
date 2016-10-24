package com.broadsoft.ums.boot.rest.muc.invite;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * @author rdokov
 *
 */
public class InviteResponse extends BaseResponse{
	
	private static final String INVITE_USER_SUCCESSFUL = "0000301";
	
	private static final String INVITE_REQUEST_PROCESSED = "Room invite request processed.";

	public InviteResponse() {
		super(INVITE_USER_SUCCESSFUL, ResponseTypes.SUCCESS, INVITE_REQUEST_PROCESSED);
	}
}

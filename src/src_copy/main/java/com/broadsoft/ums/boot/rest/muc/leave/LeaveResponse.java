package com.broadsoft.ums.boot.rest.muc.leave;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * @author rdokov
 */
public class LeaveResponse extends BaseResponse {
	
	private static final String LEAVE_ROOM_SUCCESSFUL = "0000201";
	
	private static final String LEAVE_REQUEST_PROCESSED =  "Leave room request processed";
	
	public LeaveResponse() {
		super(LEAVE_ROOM_SUCCESSFUL, ResponseTypes.SUCCESS, LEAVE_REQUEST_PROCESSED);
	}
}

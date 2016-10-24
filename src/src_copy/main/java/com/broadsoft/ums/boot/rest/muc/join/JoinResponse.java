package com.broadsoft.ums.boot.rest.muc.join;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * @author rdokov
 */
public class JoinResponse extends BaseResponse {
	
	private static final String JOIN_ROOM_SUCCESSFUL = "0000101";

	private static final String JOIN_REQUEST_VALIDATED = "Join room request processed."; 
	
	public JoinResponse(){
		super(JOIN_ROOM_SUCCESSFUL, ResponseTypes.SUCCESS, JOIN_REQUEST_VALIDATED);
	}
}

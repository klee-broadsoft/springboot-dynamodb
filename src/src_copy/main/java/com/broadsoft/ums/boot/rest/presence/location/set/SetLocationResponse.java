package com.broadsoft.ums.boot.rest.presence.location.set;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class SetLocationResponse extends BaseResponse {
	
	public SetLocationResponse() {
		super("0200003", ResponseTypes.SUCCESS, "Location Set Successfully!");
	}
}
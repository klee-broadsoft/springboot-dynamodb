package com.broadsoft.ums.boot.rest.presence.location.delete;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class DeleteLocationResponse extends BaseResponse {

	public DeleteLocationResponse() {
		super("0200004", ResponseTypes.SUCCESS, "Location Deleted!");
	}
}
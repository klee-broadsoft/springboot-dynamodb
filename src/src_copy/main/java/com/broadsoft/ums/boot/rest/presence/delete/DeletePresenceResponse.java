package com.broadsoft.ums.boot.rest.presence.delete;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class DeletePresenceResponse extends BaseResponse {

	public DeletePresenceResponse() {
		super("0200002", ResponseTypes.SUCCESS, "Presence Deleted!");
	}
}
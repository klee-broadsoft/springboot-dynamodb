package com.broadsoft.ums.boot.rest.presence.freetext.delete;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class DeleteFreeTextResponse extends BaseResponse {
	
	public DeleteFreeTextResponse() {
		super("0200006", ResponseTypes.SUCCESS, "FreeText Deleted!"); 
	}
}
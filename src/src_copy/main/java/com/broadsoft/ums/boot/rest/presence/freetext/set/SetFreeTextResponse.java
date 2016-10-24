package com.broadsoft.ums.boot.rest.presence.freetext.set;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class SetFreeTextResponse extends BaseResponse {
	
	public SetFreeTextResponse() {
		super("0200005", ResponseTypes.SUCCESS, "FreeText Set Successfully!");
	}
}
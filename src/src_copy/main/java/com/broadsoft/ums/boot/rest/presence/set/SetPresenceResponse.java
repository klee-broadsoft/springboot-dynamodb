package com.broadsoft.ums.boot.rest.presence.set;

import io.swagger.annotations.ApiModel;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * @author mgeorgiev
 *
 */
@ApiModel(value = "PresenceResponse", description = "Presence Response")
public class SetPresenceResponse  extends BaseResponse  {

	public SetPresenceResponse() {
		super("0200001", ResponseTypes.SUCCESS, "Presence Set Successfully!");

	}
}
package com.broadsoft.ums.boot.rest.presence.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.broadsoft.ums.boot.rest.Body;

/**
 * 
 * <pre>
 * {@code
 * {
 *     "show": "string",
 * }
 * 
 * </pre>
 * 
 * @author mgeorgiev
 *
 */
@ApiModel(value = "SetBody", description = "Set Body")
public class SetPresenceBody implements Body {

	@ApiModelProperty(required=true, notes="note", value="mgeorgiev@broadsoft.com", example="mgeorgiev@broadsoft.com")
	private String show;

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
}
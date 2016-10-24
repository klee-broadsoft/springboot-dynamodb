package com.broadsoft.ums.boot.rest.presence.freetext.set;

import com.broadsoft.ums.boot.rest.Body;

import io.swagger.annotations.ApiModelProperty;

public class SetFreeTextBody implements Body {

	@ApiModelProperty(required=true, notes="note", value="someFreeText", example="someFreeText")
	private String freeText;

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
}
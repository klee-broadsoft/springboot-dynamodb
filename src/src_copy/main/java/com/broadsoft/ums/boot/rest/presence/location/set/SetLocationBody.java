package com.broadsoft.ums.boot.rest.presence.location.set;

import com.broadsoft.ums.boot.rest.Body;

public class SetLocationBody implements Body {

	private Location loc;

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
}
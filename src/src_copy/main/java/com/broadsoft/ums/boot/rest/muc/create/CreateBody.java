package com.broadsoft.ums.boot.rest.muc.create;

import com.broadsoft.ums.boot.rest.Body;

/**
 * 
 * <pre>
 * {@code
 * {
 *     "fn": "string",
 *     "ln": "string",
 *     "phId": "string"
 *     "persistent": true,
 * }
 * 
 * </pre>
 * 
 * @author mgeorgiev
 *
 */
public class CreateBody implements Body {

	private String fn;
	
	private String ln;
	
	private String phId;
	
	private Boolean persistent;

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public String getLn() {
		return ln;
	}

	public void setLn(String ln) {
		this.ln = ln;
	}

	public String getPhId() {
		return phId;
	}

	public void setPhId(String phId) {
		this.phId = phId;
	}

	public Boolean isPersistent() {
		return persistent;
	}

	public void setPersistent(Boolean persistent) {
		this.persistent = persistent;
	}
}
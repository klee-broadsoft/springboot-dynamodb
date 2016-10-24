package com.broadsoft.ums.boot.rest.muc.join;

import com.broadsoft.ums.boot.rest.Body;

/**
 * 
 * <pre>
 * {@code
 *    {"fn": "first", "ln": "last","phId": "phone"}
 * </pre>
 * 
 * @author rdokov
 *
 */
public class JoinBody implements Body{

	private String fn;
	
	private String ln;
	
	private String phId;
	
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

	
}
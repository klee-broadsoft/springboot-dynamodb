package com.broadsoft.ums.boot.rest.muc.guestclient;

import com.broadsoft.ums.boot.rest.Body;


/**
 * 
 * <pre>
 * {@code
 *    {"iqId": "abcd"}
 * </pre>
 * 
 * @author jkang
 *
 */
public class GuestClientBody implements Body {
	
	String iqId;
	
	public void setIqId(String iqId) {
		this.iqId = iqId;
	}
	
	public String getIqId() {
		return this.iqId;
	}
}

package com.broadsoft.ums.boot.rest.muc.kick;

import java.util.List;

import com.broadsoft.ums.boot.rest.Body;

/**
 * 
 * <pre>
 * {@code
 * {
 *      "users": ["user1@domain.com, ..."]
 * }
 * </pre>
 * 
 * @author rdokov
 *
 */
public class KickBody implements Body{
	
	private List<String> users;

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
}
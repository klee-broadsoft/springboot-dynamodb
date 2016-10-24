package com.broadsoft.ums.boot.rest.muc.invite;

import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
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
public class InviteBody implements Body {

	@ApiParam("test name")
	private List<String> users = new ArrayList<String>();

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
}
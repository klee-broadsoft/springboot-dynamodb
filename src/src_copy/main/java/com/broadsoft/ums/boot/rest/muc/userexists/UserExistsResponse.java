package com.broadsoft.ums.boot.rest.muc.userexists;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * 
 * <pre>
 * {@code
 * {
 *   "status": {
 *       "type": "success", "code": "0000901", "message": "Check if user exists in room request processed"
 *   },
 *   "exists": true
 *   ]
 * }
 * }
 * </pre>
 * 
 * @author jkang
 */
public class UserExistsResponse extends BaseResponse {
	
	public class UserExists {
		private boolean exists;
		
		public void setUserExists(boolean exists) {
			this.exists = exists;
		}
		
		public boolean getUserExists() {
			return this.exists;
		}
	}
	
	
	private static final String CHECK_USER_EXISTS_SUCCESSFUL = "0000901";
	
	private static final String CHECK_USER_EXISTS_REQUEST_PROCESSED = "Check if user exists in room request processed";

	private UserExists ue = new UserExists();
	
	public UserExistsResponse() {
		super(CHECK_USER_EXISTS_SUCCESSFUL, ResponseTypes.SUCCESS, CHECK_USER_EXISTS_REQUEST_PROCESSED);
		ue.setUserExists(true);
	}
	
	public boolean getUserExists() {
		return ue.getUserExists();
	}
	
	public void setUserExists(boolean userExists) {
		this.ue.setUserExists(userExists);
	}
}

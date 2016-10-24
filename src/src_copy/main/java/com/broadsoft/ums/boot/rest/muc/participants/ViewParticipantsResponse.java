package com.broadsoft.ums.boot.rest.muc.participants;

import java.util.ArrayList;
import java.util.List;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

/**
 * 
 * <pre>
 * {@code
 * {
 *   "status": {
 *       "type": "success", "code": "0000001", "message": "Participant List Retrieved Successfully"
 *   },
 *   "participants": [
 *     {"roomJid": "test@muc.ihs.broadsoft.com", "jid": "jens@broadsoft.com", "firstName": "Jens", "lastName": "Voigt", "role": "moderator", "affiliation":"admin", "phoneId": "+14151112222"},
 *     {"roomJid": "test@muc.ihs.broadsoft.com", "jid": "eddy@broadsoft.com", "firstName": "Eddy", "lastName": "Merckx", "role": "participant", "affiliation":"member", "phoneId": "+16502223333"},
 *     ....
 *   ]
 * }
 * }
 * </pre>
 * 
 * @author rdokov
 */
public class ViewParticipantsResponse extends BaseResponse {
	
	public class Participant {
		private String roomJid;
		private String jid;
		private String nickname;
		private String firstName;
		private String lastName;
		private String role;
		private String affiliation;
		private String phoneId;
		public void setRoomJid(String roomJid) {
			this.roomJid = roomJid;
		}
		public String getRoomJid() {
			return roomJid;
		}
		public String getJid() {
			return jid;
		}
		public void setJid(String jid) {
			this.jid = jid;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getNickname() {
			return nickname;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public void setAffiliation(String affiliation) {
			this.affiliation = affiliation;
		}
		public String getAffiliation() {
			return affiliation;
		}
		public String getPhoneId() {
			return phoneId;
		}
		public void setPhoneId(String phoneId) {
			this.phoneId = phoneId;
		}
		
		
	}

	private static final String VIEW_PARTICIPANTS_SUCCESSFUL = "0000004";
	
	private static final String PARTICIPANTS_LIST_RETRIEVED = "2 participants Retrieved Successfully";
	
	private List<Participant> participants = new ArrayList<Participant>();
	
	public ViewParticipantsResponse() {
		
		super(VIEW_PARTICIPANTS_SUCCESSFUL, ResponseTypes.SUCCESS, PARTICIPANTS_LIST_RETRIEVED);
		Participant p = new Participant();
		p.setRoomJid("test@much.ihs.broadsoft.com");
		p.setJid("jens@broadsoft.com");
		p.setNickname("Jens Voight");
		p.setFirstName("Jens");
		p.setLastName("Voigt");
		p.setRole("moderator");
		p.setAffiliation("admin");
		p.setPhoneId("+14151112222");
		this.participants.add(p);
		
		p = new Participant();
		p.setRoomJid("test@much.ihs.broadsoft.com");
		p.setJid("eddy@broadsoft.com");
		p.setNickname("Eddy Merckx");
		p.setFirstName("Eddy");
		p.setLastName("Merckx");
		p.setRole("participant");
		p.setAffiliation("member");
		p.setPhoneId("+16502223333");
		this.participants.add(p);
		
		
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participant) {
		this.participants = participant;
	}
	
	
	
}

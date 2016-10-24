package com.broadsoft.ums.boot.rest.muc.mucqueue;

import java.util.ArrayList;
import java.util.List;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;

public class GetMucQueueResponse extends BaseResponse {
	
	public class MucInfoQueue {
		private String participantJid;
		private String firstName;
		private String lastName;
		private String role;
		private String phoneId;
		private String action;
		public String getParticpantJid() {
			return participantJid;
		}
		public void setParticipantJid(String participantJid) {
			this.participantJid = participantJid;
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
		public String getPhoneId() {
			return phoneId;
		}
		public void setPhoneId(String phoneId) {
			this.phoneId = phoneId;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		private String roomJid;
		private long createdTs;
		
		public String getRoomJid() {
			return roomJid;
		}
		public void setRoomJid(String roomJid) {
			this.roomJid = roomJid;
		}
		public long getCreatedTs() {
			return createdTs;
		}
		public void setCreatedTs(long createdTs) {
			this.createdTs = createdTs;
		}
	}
	
	public class MucGCQueue {
		private String gcJid;
		private String gcFirstName;
		private String gcLastName;
		private String timeout;
		public String getFirstName() {
			return gcFirstName;
		}
		public void setGCFirstName(String gcFirstName) {
			this.gcFirstName = gcFirstName;
		}
		public String getGCLastName() {
			return gcLastName;
		}
		public void setGCLastName(String gcLastName) {
			this.gcLastName = gcLastName;
		}
		public String getGCJid() {
			return gcJid;
		}
		public void setGCJid(String gcJid) {
			this.gcJid = gcJid;
		}
		public String getTimeout() {
			return timeout;
		}
		public void setTimeout(String timeout) {
			this.timeout = timeout;
		}
		private String roomJid;
		private long createdTs;
		
		public String getRoomJid() {
			return roomJid;
		}
		public void setRoomJid(String roomJid) {
			this.roomJid = roomJid;
		}
		public long getCreatedTs() {
			return createdTs;
		}
		public void setCreatedTs(long createdTs) {
			this.createdTs = createdTs;
		}
	}
	
	public class MucInviteQueue {
		private String inviterJid;
		private String reason;
		public String getInviterJid() {
			return inviterJid;
		}
		public void setInviterJid(String invitedJid) {
			this.inviterJid = invitedJid;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		private String roomJid;
		private long createdTs;
		
		public String getRoomJid() {
			return roomJid;
		}
		public void setRoomJid(String roomJid) {
			this.roomJid = roomJid;
		}
		public long getCreatedTs() {
			return createdTs;
		}
		public void setCreatedTs(long createdTs) {
			this.createdTs = createdTs;
		}
	}
	
	private static final String VIEW_MUC_QUEUE_SUCCESSFUL = "0000004";
	
	private static final String MUC_QUEUE_LIST_RETRIEVED = "Muc Queue Retrieved Successfully";
	
	private List<MucInfoQueue> mucInfoQueue = new ArrayList<MucInfoQueue>();
	private List<MucGCQueue> mucGCQueue = new ArrayList<MucGCQueue>();
	private List<MucInviteQueue> mucInviteQueue = new ArrayList<MucInviteQueue>();
	
	public GetMucQueueResponse() {
		
		super(VIEW_MUC_QUEUE_SUCCESSFUL, ResponseTypes.SUCCESS, MUC_QUEUE_LIST_RETRIEVED);
		MucInfoQueue mucInfoQ = new MucInfoQueue();
		mucInfoQ.setRoomJid("9ac5ac6f3f6b4c98807059c55f72e3ca-myroom-7a6c6f756973436f66742e636f6d@muc.ihs.broadsoft.com/zlouis@broadsoft.com");
		mucInfoQ.setParticipantJid("jsmith@broadsoft.com");
		mucInfoQ.setFirstName("Jay");
		mucInfoQ.setLastName("Smith");
		mucInfoQ.setRole("participant");
		mucInfoQ.setPhoneId("+1234567890");
		mucInfoQ.setAction("joined");
		mucInfoQ.setCreatedTs(476829555);
		mucInfoQueue.add(mucInfoQ);
		
		
		MucGCQueue mucGCQ = new MucGCQueue();
		mucGCQ.setRoomJid("9ac5ac6f3f6b4c98807059c55f72e3ca-myroom-7a6c6f756973436f66742e636f6d@muc.ihs.broadsoft.com/zlouis@broadsoft.com");
		mucGCQ.setGCJid("jsmith@broadsoft.com");
		mucGCQ.setGCFirstName("Jay");
		mucGCQ.setGCLastName("Smith");
		mucGCQ.setTimeout("30");
		mucGCQ.setCreatedTs(476829555);
		mucGCQueue.add(mucGCQ);
		
		
		MucInviteQueue mucInviteQ = new MucInviteQueue();
		mucInviteQ.setRoomJid("9ac5ac6f3f6b4c98807059c55f72e3ca-myroom-7a6c6f756973436f66742e636f6d@muc.ihs.broadsoft.com/zlouis@broadsoft.com");
		mucInviteQ.setInviterJid("jsmith@broadsoft.com");
		mucInviteQ.setReason("joined");
		mucInviteQ.setCreatedTs(476829555);
		mucInviteQueue.add(mucInviteQ);
	}
	
	public List<MucInfoQueue> getMucInfoQueue() {
		return mucInfoQueue;
	}
	public void setMucInfoQueue(List<MucInfoQueue> mucInfoQueue) {
		this.mucInfoQueue = mucInfoQueue;
	}
	
	public List<MucGCQueue> getMucGCQueue() {
		return mucGCQueue;
	}
	public void setMucGCQueue(List<MucGCQueue> mucGCQueue) {
		this.mucGCQueue = mucGCQueue;
	}
	
	public List<MucInviteQueue> getMucInviteQueue() {
		return mucInviteQueue;
	}
	public void setMucInviteQueue(List<MucInviteQueue> mucInviteQueue) {
		this.mucInviteQueue = mucInviteQueue;
	}
}

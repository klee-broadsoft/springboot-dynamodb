package com.broadsoft.ums.boot.rest.dynamodb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({ "msgUUID", "userJID", "senderJid", "recipientJid",
		"peerJid", "mucSenderJid", "createdTimeMs", "updatedTimeMs", "type",
		"lang", "isOutbound", "isRead", "groupThreadId", "groupMemberJids",
		"msgEncryptBytes" })
/**
 * example json:
  {
			"msgUUID": "ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f",
			"userJID": "joycelee8@broadtest.com",
			"senderJid": "joycelee8@broadtest.com",
			"recipientJid": "aliceamores@broadtest.com",
			"peerJid": "aliceamores@broadtest.com",
			"mucSenderJid": "null",
			"createdTimeMs": 1476207911105,
			"updatedTimeMs": 1476207911105,
			"type": "chat",
			"lang": "en",
			"isOutbound": "true",
			"isRead": "true",
			"groupThreadId": "",
			"groupMemberJids": "",
			"msgEncryptBytes": "d1VtSnk2d0RzNFBHcURyUG00k+XTWX94F4vLWeigdhJRQxZsg174/dJHmbr5Jd1N"
	}
 * @author klee
 *
 */
public class HistoryMsg {

	@JsonProperty("msgUUID")
	private String msgUUID;
	@JsonProperty("userJID")
	private String userJID;
	@JsonProperty("senderJid")
	private String senderJid;
	@JsonProperty("recipientJid")
	private String recipientJid;
	@JsonProperty("peerJid")
	private String peerJid;
	@JsonProperty("mucSenderJid")
	private String mucSenderJid;
	@JsonProperty("createdTimeMs")
	private long createdTimeMs;
	@JsonProperty("updatedTimeMs")
	private long updatedTimeMs;
	@JsonProperty("type")
	private String type;
	@JsonProperty("lang")
	private String lang;
	@JsonProperty("isOutbound")
	private String isOutbound;
	@JsonProperty("isRead")
	private String isRead;
	@JsonProperty("groupThreadId")
	private String groupThreadId;
	@JsonProperty("groupMemberJids")
	private String groupMemberJids;
	@JsonProperty("msgEncryptBytes")
	private String msgEncryptBytes;

	/**
	 * 
	 * @return The msgUUID
	 */
	@JsonProperty("msgUUID")
	public String getMsgUUID() {
		return msgUUID;
	}

	/**
	 * 
	 * @param msgUUID
	 *            The msgUUID
	 */
	@JsonProperty("msgUUID")
	public void setMsgUUID(String msgUUID) {
		this.msgUUID = msgUUID;
	}

	/**
	 * 
	 * @return The userJID
	 */
	@JsonProperty("userJID")
	public String getUserJID() {
		return userJID;
	}

	/**
	 * 
	 * @param userJID
	 *            The userJID
	 */
	@JsonProperty("userJID")
	public void setUserJID(String userJID) {
		this.userJID = userJID;
	}

	/**
	 * 
	 * @return The senderJid
	 */
	@JsonProperty("senderJid")
	public String getSenderJid() {
		return senderJid;
	}

	/**
	 * 
	 * @param senderJid
	 *            The senderJid
	 */
	@JsonProperty("senderJid")
	public void setSenderJid(String senderJid) {
		this.senderJid = senderJid;
	}

	/**
	 * 
	 * @return The recipientJid
	 */
	@JsonProperty("recipientJid")
	public String getRecipientJid() {
		return recipientJid;
	}

	/**
	 * 
	 * @param recipientJid
	 *            The recipientJid
	 */
	@JsonProperty("recipientJid")
	public void setRecipientJid(String recipientJid) {
		this.recipientJid = recipientJid;
	}

	/**
	 * 
	 * @return The peerJid
	 */
	@JsonProperty("peerJid")
	public String getPeerJid() {
		return peerJid;
	}

	/**
	 * 
	 * @param peerJid
	 *            The peerJid
	 */
	@JsonProperty("peerJid")
	public void setPeerJid(String peerJid) {
		this.peerJid = peerJid;
	}

	/**
	 * 
	 * @return The mucSenderJid
	 */
	@JsonProperty("mucSenderJid")
	public String getMucSenderJid() {
		return mucSenderJid;
	}

	/**
	 * 
	 * @param mucSenderJid
	 *            The mucSenderJid
	 */
	@JsonProperty("mucSenderJid")
	public void setMucSenderJid(String mucSenderJid) {
		this.mucSenderJid = mucSenderJid;
	}

	/**
	 * 
	 * @return The createdTimeMs
	 */
	@JsonProperty("createdTimeMs")
	public long getCreatedTimeMs() {
		return createdTimeMs;
	}

	/**
	 * 
	 * @param createdTimeMs
	 *            The createdTimeMs
	 */
	@JsonProperty("createdTimeMs")
	public void setCreatedTimeMs(long createdTimeMs) {
		this.createdTimeMs = createdTimeMs;
	}

	/**
	 * 
	 * @return The updatedTimeMs
	 */
	@JsonProperty("updatedTimeMs")
	public long getUpdatedTimeMs() {
		return updatedTimeMs;
	}

	/**
	 * 
	 * @param updatedTimeMs
	 *            The updatedTimeMs
	 */
	@JsonProperty("updatedTimeMs")
	public void setUpdatedTimeMs(long updatedTimeMs) {
		this.updatedTimeMs = updatedTimeMs;
	}

	/**
	 * 
	 * @return The type
	 */
	@JsonProperty("type")
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The lang
	 */
	@JsonProperty("lang")
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @param lang
	 *            The lang
	 */
	@JsonProperty("lang")
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 
	 * @return The isOutbound
	 */
	@JsonProperty("isOutbound")
	public String getIsOutbound() {
		return isOutbound;
	}

	/**
	 * 
	 * @param isOutbound
	 *            The isOutbound
	 */
	@JsonProperty("isOutbound")
	public void setIsOutbound(String isOutbound) {
		this.isOutbound = isOutbound;
	}

	/**
	 * 
	 * @return The isRead
	 */
	@JsonProperty("isRead")
	public String getIsRead() {
		return isRead;
	}

	/**
	 * 
	 * @param isRead
	 *            The isRead
	 */
	@JsonProperty("isRead")
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * 
	 * @return The groupThreadId
	 */
	@JsonProperty("groupThreadId")
	public String getGroupThreadId() {
		return groupThreadId;
	}

	/**
	 * 
	 * @param groupThreadId
	 *            The groupThreadId
	 */
	@JsonProperty("groupThreadId")
	public void setGroupThreadId(String groupThreadId) {
		this.groupThreadId = groupThreadId;
	}

	/**
	 * 
	 * @return The groupMemberJids
	 */
	@JsonProperty("groupMemberJids")
	public String getGroupMemberJids() {
		return groupMemberJids;
	}

	/**
	 * 
	 * @param groupMemberJids
	 *            The groupMemberJids
	 */
	@JsonProperty("groupMemberJids")
	public void setGroupMemberJids(String groupMemberJids) {
		this.groupMemberJids = groupMemberJids;
	}
}
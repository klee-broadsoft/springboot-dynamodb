package com.broadsoft.ums.boot.rest.dynamodb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "msg_uuid", "user_uid", "is_outbound", "is_read", "is_stale",
	"received_time", "updated_time", "history_msg" })
/**
 * 
 * example json: 
	 {
		"msg_uuid": "ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f",
		"user_uid": 14,
		"is_outbound": true,
		"is_read": true,
		"is_stale": false,
		"received_time": 1476207911105,
		"updated_time": 1476207761976,
		"history_msg": {
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
	}
 *
 * This class is used by Jackson to convert json to java pojo vice versa.
 * This clase can also be used as RequestBody for AddMsg request
 * 
 * @author klee
 *
 */
public class MessageHistory {

	@JsonProperty("msg_uuid")
	private String msgUuid;
	@JsonProperty("received_time")
	private long receivedTime;
	@JsonProperty("is_stale")
	private boolean isStale;
	@JsonProperty("user_uid")
	private int userUid;
	@JsonProperty("is_read")
	private boolean isRead;
	@JsonProperty("updated_time")
	private long updatedTime;
	@JsonProperty("is_outbound")
	private boolean isOutbound;
	@JsonProperty("history_msg")
	private HistoryMsg historyMsg;

	/**
	 * 
	 * @return The msgUuid
	 */
	@JsonProperty("msg_uuid")
	public String getMsgUuid() {
		return msgUuid;
	}

	/**
	 * 
	 * @param msgUuid
	 *            The msg_uuid
	 */
	@JsonProperty("msg_uuid")
	public void setMsgUuid(String msgUuid) {
		this.msgUuid = msgUuid;
	}

	/**
	 * 
	 * @return The receivedTime
	 */
	@JsonProperty("received_time")
	public long getReceivedTime() {
		return receivedTime;
	}

	/**
	 * 
	 * @param receivedTime
	 *            The received_time
	 */
	@JsonProperty("received_time")
	public void setReceivedTime(long receivedTime) {
		this.receivedTime = receivedTime;
	}

	/**
	 * 
	 * @return The isStale
	 */
	@JsonProperty("is_stale")
	public boolean isStale() {
		return isStale;
	}

	/**
	 * 
	 * @param isStale
	 *            The is_stale
	 */
	@JsonProperty("is_stale")
	public void setIsStale(boolean isStale) {
		this.isStale = isStale;
	}

	/**
	 * 
	 * @return The userUid
	 */
	@JsonProperty("user_uid")
	public int getUserUid() {
		return userUid;
	}

	/**
	 * 
	 * @param userUid
	 *            The user_uid
	 */
	@JsonProperty("user_uid")
	public void setUserUid(int userUid) {
		this.userUid = userUid;
	}

	/**
	 * 
	 * @return The isRead
	 */
	@JsonProperty("is_read")
	public boolean isRead() {
		return isRead;
	}

	/**
	 * 
	 * @param isRead
	 *            The is_read
	 */
	@JsonProperty("is_read")
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * 
	 * @return The updatedTime
	 */
	@JsonProperty("updated_time")
	public long getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * 
	 * @param updatedTime
	 *            The updated_time
	 */
	@JsonProperty("updated_time")
	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * 
	 * @return The isOutbound
	 */
	@JsonProperty("is_outbound")
	public boolean isOutbound() {
		return isOutbound;
	}

	/**
	 * 
	 * @param isOutbound
	 *            The is_outbound
	 */
	@JsonProperty("is_outbound")
	public void setIsOutbound(boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

	/**
	 * 
	 * @return The historyMsg
	 */
	@JsonProperty("history_msg")
	public HistoryMsg getHistoryMsg() {
		return historyMsg;
	}

	/**
	 * 
	 * @param historyMsg
	 *            The history_msg
	 */
	@JsonProperty("history_msg")
	public void setHistoryMsg(HistoryMsg historyMsg) {
		this.historyMsg = historyMsg;
	}

}
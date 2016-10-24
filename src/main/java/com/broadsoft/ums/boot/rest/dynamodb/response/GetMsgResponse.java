package com.broadsoft.ums.boot.rest.dynamodb.response;

import java.util.List;

import com.broadsoft.ums.boot.rest.BaseResponse;
import com.broadsoft.ums.boot.rest.Status.ResponseTypes;
import com.broadsoft.ums.boot.rest.dynamodb.vo.MessageHistory;

public class GetMsgResponse extends BaseResponse {
	private static final String GET_MESSAGE_SUCCESSFUL = "0000100";
	private static final String GET_MESSAGE_REQUEST_PROCESSED = "GetMsg request processed."; 	
	private List<MessageHistory> messageHistoryList = null;
		
	public GetMsgResponse(){
		super(GET_MESSAGE_SUCCESSFUL, ResponseTypes.SUCCESS, GET_MESSAGE_REQUEST_PROCESSED);
	}

	public List<MessageHistory> getMessageHistoryList() {
		return messageHistoryList;
	}

	public void setMessageHistoryList(List<MessageHistory> messageHistoryList) {
		this.messageHistoryList = messageHistoryList;
	}
}

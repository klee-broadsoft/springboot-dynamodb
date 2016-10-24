package com.broadsoft.ums.boot.rest.dynamodb.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.broadsoft.ums.boot.rest.dynamodb.vo.HistoryMsg;
import com.broadsoft.ums.boot.rest.dynamodb.vo.MessageHistory;
//import com.fasterxml.jackson.databind.ObjectMapper;
public class ScanResultUtils {
	
	private static ObjectMapper mapper = null;
	
	static {
		mapper = new ObjectMapper();
	}
	
	/**
	 * 	
	 Extract
	  
	 {
		"msg_uuid": "ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f",
		"received_time": 1476207911105,
		"is_stale": false,
		"user_uid": 14,
	    "is_read": true,
		"updated_time": 1476207761976,
		"is_outbound": true,
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
	
	 from
	 
	 {
		is_read = {
			N: 1,
		},
		updated_time = {
			N: 1476207761976,
		},
		is_outbound = {
			N: 1,
		},
		history_msg = {
			S: {
				"HistoryMsg": {
					"msgUUID": "ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f",
					"userJID": "joycelee8@broadtest.com",
					"senderJid": "joycelee8@broadtest.com",
					"recipientJid": "aliceamores@broadtest.com",
					"peerJid": "aliceamores@broadtest.com",
					"mucSenderJid": "null",
					"createdTimeMs": "1476207911105",
					"updatedTimeMs": "1476207911105",
					"type": "chat",
					"lang": "en",
					"isOutbound": "true",
					"isRead": "true",
					"groupThreadId": "",
					"groupMemberJids": "",
					"msgEncryptBytes": "d1VtSnk2d0RzNFBHcURyUG00k+XTWX94F4vLWeigdhJRQxZsg174/dJHmbr5Jd1N"
				}
			},
		},
		msg_uuid = {
			S: f53a9050 - f231 - 4264 - 9993 - 922 da3f22be4,
		},
		received_time = {
			N: 1476207761976,
		},
		is_stale = {
			N: 0,
		},
		user_uid = {
			N: 14,
		}
	}
	
	 * @param scanResult
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	
	
	public static List<MessageHistory> extractMessageHistoryList (ScanResult sr) throws JsonParseException, JsonMappingException, IOException {
		
		List<MessageHistory> mhList = new ArrayList<MessageHistory>();

		String histMsgJson = null;
		HistoryMsg hm = null;
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		List<Map<String, AttributeValue>> items = sr.getItems();
		for (Map<String, AttributeValue> item : items) {
			MessageHistory mh = new MessageHistory();
			mh.setMsgUuid(item.get("msg_uuid").getS());
			mh.setUserUid(Integer.parseInt(item.get("user_uid").getN()));
			mh.setIsOutbound(item.get("is_outbound").getN().equals("1"));
			mh.setIsRead(item.get("is_read").getN().equals("1"));
			mh.setIsStale(item.get("is_stale").getN().equals("1"));
			mh.setReceivedTime(Long.parseLong(item.get("received_time").getN()));
			mh.setUpdatedTime(Long.parseLong(item.get("updated_time").getN()));
			//mh.setHistoryMsg(item.get("history_msg").getS());
			histMsgJson = item.get("history_msg").getS();
			hm = mapper.readValue(histMsgJson, HistoryMsg.class);
			mh.setHistoryMsg(hm);
			mhList.add(mh);	
		}	
		return mhList;	
	}
	
	public static Map<String, AttributeValue> newItem (MessageHistory mh) throws JsonGenerationException, JsonMappingException, IOException {

		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("msg_uuid", new AttributeValue(mh.getMsgUuid()));
        item.put("user_uid", new AttributeValue().withN(Integer.toString(mh.getUserUid())));
        item.put("received_time", new AttributeValue().withN(Long.toString(mh.getReceivedTime())));
        item.put("updated_time", new AttributeValue().withN(Long.toString(mh.getUpdatedTime())));
        item.put("is_outbound", new AttributeValue().withN(mh.isOutbound()?"1":"0"));
        item.put("is_stale", new AttributeValue().withN(mh.isStale()?"1":"0"));
        item.put("is_read", new AttributeValue().withN(mh.isStale()?"1":"0"));
        String histMsgJson = mapper.writeValueAsString(mh.getHistoryMsg());
        item.put("history_msg", new AttributeValue(histMsgJson));

		return item;
	}

}

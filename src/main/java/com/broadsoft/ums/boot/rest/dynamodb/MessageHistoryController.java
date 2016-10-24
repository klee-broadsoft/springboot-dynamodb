package com.broadsoft.ums.boot.rest.dynamodb;

import java.io.IOException;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.broadsoft.ums.boot.Application;
import com.broadsoft.ums.boot.rest.UmsController;
import com.broadsoft.ums.boot.rest.dynamodb.response.AddMsgResponse;
import com.broadsoft.ums.boot.rest.dynamodb.response.GetMsgResponse;
import com.broadsoft.ums.boot.rest.dynamodb.response.MarkAsReadResponse;
import com.broadsoft.ums.boot.rest.dynamodb.utils.ScanResultUtils;
import com.broadsoft.ums.boot.rest.dynamodb.vo.MessageHistory;
import com.broadsoft.ums.boot.rest.exception.UmsException;

@Controller
@EnableAutoConfiguration
public class MessageHistoryController extends UmsController {
	
	@ApiOperation(value = "addMessages", nickname = "addMessages", notes = "Add Messages")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AddMsgResponse.class)}) 
	@RequestMapping(method = RequestMethod.POST, path="/dynamodb/v1/msg/",  produces = "application/json")
	public @ResponseBody AddMsgResponse addMsgs(@RequestBody MessageHistory messageHistory) throws UmsException, JsonGenerationException, JsonMappingException, IOException {
		
		// parse request body, add message to dynamodb
		String tableName = "dynamodb_message_history";
		
		Map<String, AttributeValue> item = ScanResultUtils.newItem(messageHistory);
		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
		PutItemResult putItemResult = Application.getDynamoDB().putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);
		return new AddMsgResponse();
	}
	
	@ApiOperation(value = "getMessages", nickname = "getMessages", notes = "get messages for the given user and which are newer than given time")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetMsgResponse.class)}) 
	@RequestMapping(method = RequestMethod.GET, path="/dynamodb/v1/msg/{userUid}?time={unixTimeMsec}",  produces = "application/json")
	public @ResponseBody GetMsgResponse getMsgs(@PathVariable String userUid, @RequestParam String unixTimeMsec) throws UmsException {
		

		return new GetMsgResponse();
	}
	
	@ApiOperation(value = "markAsReadMessages", nickname = "markAsReadMessages", notes = "mark a message as read for the given msg_uuid")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = MarkAsReadResponse.class)}) 
	@RequestMapping(method = RequestMethod.PATCH, path="/dynamodb/v1/msg/{msgUuid}",  produces = "application/json")
	public @ResponseBody MarkAsReadResponse markAsRead(@PathVariable String msgUuid) throws UmsException {
		

		return new MarkAsReadResponse();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageHistory Controller - Implementation of MessageHistory REST API";
	}

}

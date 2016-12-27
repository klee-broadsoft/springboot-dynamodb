package com.broadsoft.ums.boot.rest.dynamodb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.broadsoft.ums.boot.Application;
import com.broadsoft.ums.boot.rest.UmsController;
import com.broadsoft.ums.boot.rest.dynamodb.response.AddMsgResponse;
import com.broadsoft.ums.boot.rest.dynamodb.response.GetMsgResponse;
import com.broadsoft.ums.boot.rest.dynamodb.response.MarkAsReadResponse;
import com.broadsoft.ums.boot.rest.dynamodb.utils.ScanResultUtils;
import com.broadsoft.ums.boot.rest.dynamodb.vo.HistoryMsg;
import com.broadsoft.ums.boot.rest.dynamodb.vo.MessageHistory;
import com.broadsoft.ums.boot.rest.exception.UmsException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.codehaus.jackson.map.ObjectMapper;

@Controller
@EnableAutoConfiguration
public class MessageHistoryController extends UmsController {
	
	protected static ObjectMapper mapper = null;
	protected static Gson gson = null;
	MessageHistoryController() {
		mapper = new ObjectMapper();
		gson = new GsonBuilder().create();
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String tableName = "dynamodb_message_history";
    private static AmazonDynamoDBClient dynamoDB;
	
    public static AmazonDynamoDBClient getDynamoDB() {
		return dynamoDB;
	}

	public static void setDynamoDB(AmazonDynamoDBClient dynamoDB) {
		MessageHistoryController.dynamoDB = dynamoDB;
	}

	private static void init() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/klee/.aws/credentials).
         */
        AWSCredentials credentials = null;

        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/klee/.aws/credentials), and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
    }
	
	@ApiOperation(value = "addMessages", nickname = "addMessages", notes = "Add Messages")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AddMsgResponse.class)}) 
	@RequestMapping(method = RequestMethod.POST, path="/dynamodb/v1/msg/",  produces = "application/json")
	public @ResponseBody AddMsgResponse addMsgs(@RequestBody MessageHistory mh) throws UmsException, JsonGenerationException, JsonMappingException, IOException {

//		// parse request body, add message to dynamodb
//		String tableName = "dynamodb_message_history";
//		
		Map<String, AttributeValue> item = ScanResultUtils.newItem(mh);
		
		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);
		return new AddMsgResponse(putItemResult);
	}
	
	@ApiOperation(value = "getMessages", nickname = "getMessages", notes = "get messages for the given user and which are newer than given time")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = GetMsgResponse.class)}) 
	@RequestMapping(method = RequestMethod.GET, path="/dynamodb/v1/msg/{userUid}",  produces = "application/json")
	public @ResponseBody GetMsgResponse getMsgs(@PathVariable String userUid, @RequestParam("time") String unixTimeMsec) throws UmsException {

		//String tableName = "dynamodb_message_history";
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		
		Condition condition1 = new Condition().withComparisonOperator(
				ComparisonOperator.GE.toString()).withAttributeValueList(
				new AttributeValue().withN(unixTimeMsec));
		Condition condition2 = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withN(userUid));

		scanFilter.put("received_time", condition1);
		scanFilter.put("user_uid", condition2);
		
		ScanRequest scanRequest = new ScanRequest(tableName)
				.withScanFilter(scanFilter);
		ScanResult scanResult = Application.getDynamoDB().scan(scanRequest);
		System.out.println("Result: " + scanResult);
		
		
		List<MessageHistory> mhList = new ArrayList<MessageHistory>();
		GetMsgResponse response = new GetMsgResponse();
		mhList = ScanResultUtils.extractMessageHistoryList(scanResult);
		
		response.setMessageHistoryList(mhList);

		return response;
	}
	
	// TODO:  NOT WORKING YET - error - The provided key element does not match the schema 
	// 
	@ApiOperation(value = "markAsReadMessages", nickname = "markAsReadMessages", notes = "mark a message as read for the given msg_uuid")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = MarkAsReadResponse.class)}) 
	@RequestMapping(method = RequestMethod.PATCH, path="/dynamodb/v1/msg/{msgUuid}",  produces = "application/json")
	public @ResponseBody MarkAsReadResponse markAsRead(@PathVariable String msgUuid) throws UmsException {
		
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put("msg_uuid", new AttributeValue().withS(msgUuid));
		
		Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<String, AttributeValueUpdate>();
		attributeUpdates.put("is_read", new AttributeValueUpdate(new AttributeValue().withN("1"), AttributeAction.PUT));
		
		//UpdateItemRequest updateReq = new UpdateItemRequest(tableName, key, attributeUpdates);
		UpdateItemRequest updateReq = new UpdateItemRequest().withTableName(tableName).withKey(key).withAttributeUpdates(attributeUpdates);
		UpdateItemResult updateRes = Application.getDynamoDB().updateItem(updateReq);
		

		return new MarkAsReadResponse(updateRes);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageHistory Controller - Implementation of MessageHistory REST API";
	}

}

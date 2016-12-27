package com.broadsoft.ums.boot;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.broadsoft.ums.boot.rest.UmsController;

/**
 * Spring Boot application to experiment message_history table in DynamoDB as a microservice
 * 
 * Arguments:
 * <ul>
 *   <li>? - help</li?>
 *   <li>-v - version</li>
 * </ul>
 * 
 *
 */
@EnableSwagger2
@ComponentScan("com.broadsoft.ums.boot")
@SpringBootApplication
public class Application {

	public static String tableName = "dynamodb_message_history";
    private static AmazonDynamoDBClient dynamoDB;
	
    public static AmazonDynamoDBClient getDynamoDB() {
		return dynamoDB;
	}

	public static void setDynamoDB(AmazonDynamoDBClient dynamoDB) {
		Application.dynamoDB = dynamoDB;
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
	
	private static void createTable(String tableName)
			throws InterruptedException {
		CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName);
		
		//ProvisionedThroughput
		createTableRequest.setProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits((long)5).withWriteCapacityUnits((long)5));
		
		//AttributeDefinitions
		ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("msg_uuid").withAttributeType("S"));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("user_uid").withAttributeType("N"));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("received_time").withAttributeType("N"));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("updated_time").withAttributeType("N"));
		
		createTableRequest.setAttributeDefinitions(attributeDefinitions);
		// Table KeySchema
		ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
		tableKeySchema.add(new KeySchemaElement().withAttributeName("user_uid").withKeyType(KeyType.HASH));  //Partition key
		tableKeySchema.add(new KeySchemaElement().withAttributeName("received_time").withKeyType(KeyType.RANGE));  //Sort key
		
		createTableRequest.setKeySchema(tableKeySchema);
		
		// Local Index KeySchema
		ArrayList<KeySchemaElement> localIndexKeySchema = new ArrayList<KeySchemaElement>();
		localIndexKeySchema.add(new KeySchemaElement().withAttributeName("user_uid").withKeyType(KeyType.HASH));  //Partition key
		localIndexKeySchema.add(new KeySchemaElement().withAttributeName("updated_time").withKeyType(KeyType.RANGE));  //Sort key
		
		// Global Index KeySchema
		ArrayList<KeySchemaElement> globalIndexKeySchema = new ArrayList<KeySchemaElement>();
		globalIndexKeySchema.add(new KeySchemaElement().withAttributeName("msg_uuid").withKeyType(KeyType.HASH));  //Partition key
		
		// Projected attributes
		Projection projection = new Projection().withProjectionType(ProjectionType.INCLUDE);
		ArrayList<String> nonKeyAttributes = new ArrayList<String>();
		nonKeyAttributes.add("is_read");
		nonKeyAttributes.add("is_stale");
		nonKeyAttributes.add("is_outbound");
		nonKeyAttributes.add("history_msg"); // json representation of HistoryMsg - fed from MessageHistoryPlugin.createMsgHistory()
		projection.setNonKeyAttributes(nonKeyAttributes);
		
		// Local Secondary Index
		LocalSecondaryIndex localSecondaryIndex = new LocalSecondaryIndex()
		    .withIndexName("updated_time_idx").withKeySchema(localIndexKeySchema).withProjection(projection);
		
		ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
		localSecondaryIndexes.add(localSecondaryIndex);
		createTableRequest.setLocalSecondaryIndexes(localSecondaryIndexes);

		// Global Secondary Index
		GlobalSecondaryIndex globalSecondaryIndex = new GlobalSecondaryIndex()
		    .withIndexName("msg_uuid_idx").withKeySchema(globalIndexKeySchema).withProjection(projection);
		
		ArrayList<GlobalSecondaryIndex> globalSecondaryIndexes = new ArrayList<GlobalSecondaryIndex>();
		globalSecondaryIndexes.add(globalSecondaryIndex);
		globalSecondaryIndex.setProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits((long)5).withWriteCapacityUnits((long)5));
		createTableRequest.setGlobalSecondaryIndexes(globalSecondaryIndexes);
		
		// create table
		TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
		
		// wait for the table to move into ACTIVE state
		TableUtils.waitUntilActive(dynamoDB, tableName);
		
		// Describe our new table
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
		TableDescription tableDescription = (dynamoDB).describeTable(describeTableRequest).getTable();
		System.out.println("Table Description: " + tableDescription);
	}

	private static void queryTable(String tableName) {
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		
		Condition condition1 = new Condition().withComparisonOperator(
				ComparisonOperator.GT.toString()).withAttributeValueList(
				new AttributeValue().withN("1476207529273"));
		Condition condition2 = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withN("1"));

		scanFilter.put("received_time", condition1);
		scanFilter.put("is_read", condition2);
		
		ScanRequest scanRequest = new ScanRequest(tableName)
				.withScanFilter(scanFilter);
		ScanResult scanResult = dynamoDB.scan(scanRequest);
		System.out.println("Result: " + scanResult);
	}
	
	private static void queryTable2(String tableName) {
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		
		Condition condition1 = new Condition().withComparisonOperator(
				ComparisonOperator.GE.toString()).withAttributeValueList(
				new AttributeValue().withN("1476207785493"));
		Condition condition2 = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withN("14"));

		scanFilter.put("received_time", condition1);
		scanFilter.put("user_uid", condition2);
		
		ScanRequest scanRequest = new ScanRequest(tableName)
				.withScanFilter(scanFilter);
		ScanResult scanResult = dynamoDB.scan(scanRequest);
		System.out.println("Result2: " + scanResult);
		List<Map<String, AttributeValue>> items = scanResult.getItems();
		for (Map<String, AttributeValue> item: items) {
			Set<String> keys = item.keySet();
			for (String key: keys) {
				AttributeValue attr = item.get(key);
				String val = null;
				// Assuming values are N or S for now
				if ((val = attr.getN())==null) {
					val = attr.getS();
				} 
				System.out.println("*** "+key+" = ["+val+"]");
			}
		}
	}

	private static void addMessages(String tableName) {
		Map<String, AttributeValue> item = newItem(
				"36522143-71fd-4cdf-b6b5-e8bab8811083",
				14,
				1476204964560L,
				1476204964560L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"0fb24455-3f46-4943-bf92-7fc6a22e8b70\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910622\", \"updatedTimeMs\": \"1476207910622\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"TVozRzg5ZFpuejN2cUZOZJ1nyCf599tFv5z+qjZTSd9/MJ2y1SsHUroGDHjxpwDM\"}}");
		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"36522143-71fd-4cdf-b6b5-e8bab8811083",
				16,
				1476204964560L,
				1476204964560L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"0fb24455-3f46-4943-bf92-7fc6a22e8b70\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910622\", \"updatedTimeMs\": \"1476207910622\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"b1I5MWZlVW5ra3dwWmVIZkGaVxD3SGBmu5gpSrgpcyC8pdyKLSwteVM/jqygPej2\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"e887d899-cf7e-4901-947b-6ecf89269b7e",
				14,
				1476206064159L,
				1476206064159L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"c79a5d78-99bc-47f0-875b-9cfe821ac5bd\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910680\", \"updatedTimeMs\": \"1476207910680\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"Z0FjdkRRaEdjOE9TZnBvVJaGcUtTM1xGCWXYEIBkmYkcAfVb3TI9c+mLMCADk8vd\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"e887d899-cf7e-4901-947b-6ecf89269b7e",
				16,
				1476206064159L,
				1476206064159L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"c79a5d78-99bc-47f0-875b-9cfe821ac5bd\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910680\", \"updatedTimeMs\": \"1476207910680\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"ZWYzVkhGMU81bUwxazRuUK7xYK69Lk2pX3lOcgdIDN5dxkkZwhkVb7q2bEyEuejk\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"fd14918e-99c6-4515-9c55-3b69884fd11a",
				16,
				1476206132768L,
				1476206132768L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"5fd72c8f-b6f2-466c-a718-8f8436d12963\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910758\", \"updatedTimeMs\": \"1476207910758\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"Y2pqdVhDSGRFRFBiQmk1ZC9AJj9QvXqeJIEIrVPTen25y2elccrYy/U4h4MeqlBM\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"fd14918e-99c6-4515-9c55-3b69884fd11a",
				14,
				1476206132768L,
				1476206132768L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"5fd72c8f-b6f2-466c-a718-8f8436d12963\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910758\", \"updatedTimeMs\": \"1476207910758\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"b0V5c3hDUUJqQUpJR2tnMqtuKFbz+bZM+jnEY3385AFSjwof/xZgqnLdP/uPSmGb\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"2e44b44e-e6e5-489e-9da8-36bfade76e5d",
				14,
				1476206334805L,
				1476206334805L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"8d5fa7c1-9d4d-4e7a-ab93-045b9c87e53a\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910811\", \"updatedTimeMs\": \"1476207910811\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"YUgzVUd6MzJWUHNzZUlMa0NqXUt8mp8XfxLJJh0U9ucdBd/U41rtf3YTCSUyhp5Q\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"2e44b44e-e6e5-489e-9da8-36bfade76e5d",
				16,
				1476206334805L,
				1476206334805L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"8d5fa7c1-9d4d-4e7a-ab93-045b9c87e53a\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910811\", \"updatedTimeMs\": \"1476207910811\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"N2x4SU14NnhOMDRvdUdhYoxs2lcEcyDvjmQx3lAhNTZXT6/AkK48C/xalrRUP9+V\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"f2a547e5-630f-40ce-9137-17dcc6fa02f0",
				16,
				1476206402626L,
				1476206402626L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"4445be22-bc1f-4b75-af0c-bc1d7df75664\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910894\", \"updatedTimeMs\": \"1476207910894\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"eVRsZFZhbERSWXNkM2RMWnXCJCNR4sKB8hGJcO+NYDT3pYIFk4x/poJ1YC6wQR0d\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"f2a547e5-630f-40ce-9137-17dcc6fa02f0",
				14,
				1476206402626L,
				1476206402626L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"4445be22-bc1f-4b75-af0c-bc1d7df75664\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910894\", \"updatedTimeMs\": \"1476207910894\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"QXkwYnZhd2x3V29JQWZ3eXtDy1M6zwTLrfre2KiJGv2KZEYa/7gWUWLJbgKmL3rW\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"adc333bf-7535-4b4e-aceb-ba336cd0bea4",
				16,
				1476207529273L,
				1476207529273L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"b8502bf2-ff4a-4918-a153-2389bcad8c4a\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910966\", \"updatedTimeMs\": \"1476207910966\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"Szl4MFlRb3VHVDZFWHNBMCDC1NaLDt+48gGjmqpHBbN7rgeNXUxXNyOgBURRnSlm\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"adc333bf-7535-4b4e-aceb-ba336cd0bea4",
				14,
				1476207529273L,
				1476207529273L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"b8502bf2-ff4a-4918-a153-2389bcad8c4a\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207910966\", \"updatedTimeMs\": \"1476207910966\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"dE9EMlNJSzRWRndKakRVV5hNKARB4hNHOSkv7X2AfEfx6A70LnW6KfgX1zRnsowT\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"339d2e75-7727-4c59-818d-f3a287c5f2a5",
				14,
				1476207729188L,
				1476207729188L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"c467075c-e170-4bbb-b0bc-82645517fa10\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911040\", \"updatedTimeMs\": \"1476207911040\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"Q2dtQWJDZEZXU1RKTkxXZYSWaQ7VxWgd+lsJXQp1At3S5jCI7PBB5keTtmoVnr2/\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"339d2e75-7727-4c59-818d-f3a287c5f2a5",
				16,
				1476207729188L,
				1476207729188L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"c467075c-e170-4bbb-b0bc-82645517fa10\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911040\", \"updatedTimeMs\": \"1476207911040\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"bXVHbHUyTVRUSmtXYkxwNszwI43f5bDOF8FZc/Te9drgRnLWg2O0lVjCv6FKi99g\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"f53a9050-f231-4264-9993-922da3f22be4",
				14,
				1476207761976L,
				1476207761976L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911105\", \"updatedTimeMs\": \"1476207911105\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"d1VtSnk2d0RzNFBHcURyUG00k+XTWX94F4vLWeigdhJRQxZsg174/dJHmbr5Jd1N\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"f53a9050-f231-4264-9993-922da3f22be4",
				16,
				1476207761976L,
				1476207761976L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"ff8c8bc0-1abe-4fd3-af41-1f4b65ef9b0f\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911105\", \"updatedTimeMs\": \"1476207911105\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"dGh3bXh3WG1DYzJpQ0JwSdJD8Z9Cayyn+nN8a4DtHqgoemaHFAzJM1jSXXwSNMWs\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"42738906-38a3-4619-8249-285497f7b1a9",
				14,
				1476207785493L,
				1476207785493L,
				true,
				false,
				true,
				"{ \"HistoryMsg\": {\"msgUUID\": \"4ec7cb7b-2669-49e6-bd51-4035dcdbad16\", \"userJID\": \"joycelee8@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"aliceamores@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911173\", \"updatedTimeMs\": \"1476207911173\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"true\", \"isRead\": \"true\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"SXU2SVBwQzVPNG50SUFtWXYVN/qmJJc9mtYp5RiG9hVyDLMaoIZTY+iaGO/3oOED\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		item = newItem(
				"42738906-38a3-4619-8249-285497f7b1a9",
				16,
				1476207785493L,
				1476207785493L,
				false,
				false,
				false,
				"{ \"HistoryMsg\": {\"msgUUID\": \"4ec7cb7b-2669-49e6-bd51-4035dcdbad16\", \"userJID\": \"aliceamores@broadtest.com\", \"senderJid\": \"joycelee8@broadtest.com\", \"recipientJid\": \"aliceamores@broadtest.com\", \"peerJid\": \"joycelee8@broadtest.com\", \"mucSenderJid\": \"null\", \"createdTimeMs\": \"1476207911173\", \"updatedTimeMs\": \"1476207911173\", \"type\": \"chat\", \"lang\": \"en\", \"isOutbound\": \"false\", \"isRead\": \"false\", \"groupThreadId\": \"\", \"groupMemberJids\": \"\", \"msgEncryptBytes\": \"VVBMR3BuTmd0MGpaTkJOevmw7sFuOONbe13MfgW6wwqKbqb6t3iivc6NiR0fRf/c\"}}");
		putItemRequest = new PutItemRequest(tableName, item);
		putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);
	}

//	private static String createTable() throws InterruptedException {
//		String tableName = "dynamodb_message_history";
//
//		createTable(tableName);
//        /*******************************************
//		Table Description: {
//			AttributeDefinitions: [{
//				AttributeName: msg_uuid,
//				AttributeType: S
//			}, {
//				AttributeName: received_time,
//				AttributeType: N
//			}, {
//				AttributeName: updated_time,
//				AttributeType: N
//			}, {
//				AttributeName: user_uid,
//				AttributeType: S
//			}],
//			TableName: imp_message_history,
//			KeySchema: [{
//				AttributeName: user_uid,
//				KeyType: HASH
//			}, {
//				AttributeName: received_time,
//				KeyType: RANGE
//			}],
//			TableStatus: ACTIVE,
//			CreationDateTime: Mon Oct 10 16: 48: 27 PDT 2016,
//			ProvisionedThroughput: {
//				NumberOfDecreasesToday: 0,
//				ReadCapacityUnits: 5,
//				WriteCapacityUnits: 5
//			},
//			TableSizeBytes: 0,
//			ItemCount: 0,
//			TableArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history,
//			LocalSecondaryIndexes: [{
//				IndexName: updated_time_idx,
//				KeySchema: [{
//					AttributeName: user_uid,
//					KeyType: HASH
//				}, {
//					AttributeName: updated_time,
//					KeyType: RANGE
//				}],
//				Projection: {
//					ProjectionType: INCLUDE,
//					NonKeyAttributes: [is_outbound, is_stale, is_read, history_msg]
//				},
//				IndexSizeBytes: 0,
//				ItemCount: 0,
//				IndexArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history / index / updated_time_idx
//			}],
//			GlobalSecondaryIndexes: [{
//				IndexName: msg_uuid_idx,
//				KeySchema: [{
//					AttributeName: msg_uuid,
//					KeyType: HASH
//				}],
//				Projection: {
//					ProjectionType: INCLUDE,
//					NonKeyAttributes: [is_outbound, is_stale, is_read, history_msg]
//				},
//				IndexStatus: ACTIVE,
//				ProvisionedThroughput: {
//					NumberOfDecreasesToday: 0,
//					ReadCapacityUnits: 5,
//					WriteCapacityUnits: 5
//				},
//				IndexSizeBytes: 0,
//				ItemCount: 0,
//				IndexArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history / index / msg_uuid_idx
//			}],
//		}
//         *******************************************/
//		return tableName;
//	}

	private static Map<String, AttributeValue> newItem(String msgUuid,
			int userUid, long receivedTime, long updatedTime,
			boolean isOutbound, boolean isStale, boolean isRead,
			String historyMsgJson) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("msg_uuid", new AttributeValue(msgUuid));
        item.put("user_uid", new AttributeValue().withN(Integer.toString(userUid)));
        item.put("received_time", new AttributeValue().withN(Long.toString(receivedTime)));
        item.put("updated_time", new AttributeValue().withN(Long.toString(updatedTime)));
        item.put("is_outbound", new AttributeValue().withN(isOutbound?"1":"0"));
        item.put("is_stale", new AttributeValue().withN(isStale?"1":"0"));
        item.put("is_read", new AttributeValue().withN(isRead?"1":"0"));
        item.put("history_msg", new AttributeValue(historyMsgJson));

        return item;
    }

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		if(args.length > 0) {
			logo();
			if("?".equals(args[0])) {
				help();
				System.exit(0);
			} else if("-v".equalsIgnoreCase(args[0])) {
				version();
				System.exit(0);
			} 
		}
		
//		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//
//		logo();
//
//		info(context);
		
		init();
		
       try {

        	//String tableName = "dynamodb_message_history";
			
			createTable(tableName);
			
			addMessages(tableName);

			queryTable(tableName);
			queryTable2(tableName);

		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		logo();

		info(context);

	}

	@Bean
	public Docket umsApi() {

		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("ums")
		.apiInfo(apiInfo())
		.select()
		.paths(PathSelectors.any())
		.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
		.title("UMS REST Sample")
		.description("UMS REST Sample")
		.termsOfServiceUrl("FREE for BSFT devs")
		.contact("mgeorgiev@broadsoft.com")
		.license("BSFT")
		.licenseUrl("http://broadsoft.com")
		.version("1.0")
		.build();
	}

	private static void logo() {
		System.out.println();
		System.out.println();

		System.out.println("UUUUUUUU     UUUUUUUMMMMMMMM               MMMMMMMM  SSSSSSSSSSSSSSS ");
		System.out.println("U::::::U     U::::::M:::::::M             M:::::::MSS:::::::::::::::S");
		System.out.println("U::::::U     U::::::M::::::::M           M::::::::S:::::SSSSSS::::::S");
		System.out.println("UU:::::U     U:::::UM:::::::::M         M:::::::::S:::::S     SSSSSSS");
		System.out.println(" U:::::U     U:::::UM::::::::::M       M::::::::::S:::::S            ");
		System.out.println(" U:::::D     D:::::UM:::::::::::M     M:::::::::::S:::::S            ");
		System.out.println(" U:::::D     D:::::UM:::::::M::::M   M::::M:::::::MS::::SSSS         ");
		System.out.println(" U:::::D     D:::::UM::::::M M::::M M::::M M::::::M SS::::::SSSSS    ");
		System.out.println(" U:::::D     D:::::UM::::::M  M::::M::::M  M::::::M   SSS::::::::SS  ");
		System.out.println(" U:::::D     D:::::UM::::::M   M:::::::M   M::::::M      SSSSSS::::S ");
		System.out.println(" U:::::D     D:::::UM::::::M    M:::::M    M::::::M           S:::::S");
		System.out.println(" U::::::U   U::::::UM::::::M     MMMMM     M::::::M           S:::::S");
		System.out.println(" U:::::::UUU:::::::UM::::::M               M::::::SSSSSSS     S:::::S");
		System.out.println("  UU:::::::::::::UU M::::::M               M::::::S::::::SSSSSS:::::S");
		System.out.println("    UU:::::::::UU   M::::::M               M::::::S:::::::::::::::SS ");
		System.out.println("      UUUUUUUUU     MMMMMMMM               MMMMMMMMSSSSSSSSSSSSSSS   ");

		System.out.println();
		System.out.println();
		System.out.println("Broadsoft Gateway Emulator");
		System.out.println();
		System.out.println("for more information run with \"?\" as command line argument");
		System.out.println();
	}

	private static void help() {
		System.out.println();
		System.out.println();
		System.out.println("Help");
		System.out.println();
		System.out.println("-v version");
		System.out.println();
		System.out.println("API is accessible on: http://localhost:8080");
		System.out.println("API doc is accessible on: http://localhost:8080/swagger-ui.html");
		System.out.println();
	}

	private static void version() {
		System.out.println("version 1.0");
		System.out.println();
	}

	private static void info(ConfigurableApplicationContext context) {
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println();
		try {
			System.out.println("REST API is available on:");
			System.out.println("\thttp://" + Inet4Address.getLocalHost().getHostAddress() + ":8080");
			System.out.println("\thttp://localhost:8080");
			System.out.println("REST API documentation and REST HTML client are available on:");
			System.out.println("\thttp://" + Inet4Address.getLocalHost().getHostAddress() + ":8080/swagger-ui.html");
			System.out.println("\thttp://localhost:8080/swagger-ui.html");
		} catch (UnknownHostException e) {
			System.err.println("Cannot find IP Address of the current machine");
		}

		System.out.println();
		System.out.println("Supported REST APIs:");

		String[] beanNames = context.getBeanNamesForType(UmsController.class);

		for (String beanName : beanNames) {
			UmsController bean = (UmsController) context.getBean(beanName);
			System.out.println("\t" + bean.toString());
		}
	}
}
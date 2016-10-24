package com.broadsoft.ums.boot.rest.dynamodb;
/*
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
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
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
public class AmazonDynamoDBSample {

    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (/Users/klee/.aws/credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */

    static AmazonDynamoDBClient dynamoDB;

    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
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

    public static void main(String[] args) throws Exception {
        init();

        try {

        	String tableName = "imp_message_history";
			
			createTable(tableName);
			
			addMessages(tableName);

			queryTable(tableName);

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
        }
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

	private static String createTable() throws InterruptedException {
		String tableName = "imp_message_history";

		createTable(tableName);
        /*******************************************
		Table Description: {
			AttributeDefinitions: [{
				AttributeName: msg_uuid,
				AttributeType: S
			}, {
				AttributeName: received_time,
				AttributeType: N
			}, {
				AttributeName: updated_time,
				AttributeType: N
			}, {
				AttributeName: user_uid,
				AttributeType: S
			}],
			TableName: imp_message_history,
			KeySchema: [{
				AttributeName: user_uid,
				KeyType: HASH
			}, {
				AttributeName: received_time,
				KeyType: RANGE
			}],
			TableStatus: ACTIVE,
			CreationDateTime: Mon Oct 10 16: 48: 27 PDT 2016,
			ProvisionedThroughput: {
				NumberOfDecreasesToday: 0,
				ReadCapacityUnits: 5,
				WriteCapacityUnits: 5
			},
			TableSizeBytes: 0,
			ItemCount: 0,
			TableArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history,
			LocalSecondaryIndexes: [{
				IndexName: updated_time_idx,
				KeySchema: [{
					AttributeName: user_uid,
					KeyType: HASH
				}, {
					AttributeName: updated_time,
					KeyType: RANGE
				}],
				Projection: {
					ProjectionType: INCLUDE,
					NonKeyAttributes: [is_outbound, is_stale, is_read, history_msg]
				},
				IndexSizeBytes: 0,
				ItemCount: 0,
				IndexArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history / index / updated_time_idx
			}],
			GlobalSecondaryIndexes: [{
				IndexName: msg_uuid_idx,
				KeySchema: [{
					AttributeName: msg_uuid,
					KeyType: HASH
				}],
				Projection: {
					ProjectionType: INCLUDE,
					NonKeyAttributes: [is_outbound, is_stale, is_read, history_msg]
				},
				IndexStatus: ACTIVE,
				ProvisionedThroughput: {
					NumberOfDecreasesToday: 0,
					ReadCapacityUnits: 5,
					WriteCapacityUnits: 5
				},
				IndexSizeBytes: 0,
				ItemCount: 0,
				IndexArn: arn: aws: dynamodb: us - west - 2: 420493971485: table / imp_message_history / index / msg_uuid_idx
			}],
		}
         *******************************************/
		return tableName;
	}

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

}

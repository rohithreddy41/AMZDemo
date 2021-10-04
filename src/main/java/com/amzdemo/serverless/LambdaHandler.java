package com.amzdemo.serverless;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.Stack;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogGroupsResponse;
import software.amazon.awssdk.services.cloudwatchlogs.model.LogGroup;

import java.util.List;
import java.util.Map;


// Handler value: example.Handler
public class LambdaHandler implements RequestHandler<Map<String,String>, String> {
    @Override
    public String handleRequest(Map<String,String> event, Context context)
    {
        LambdaLogger logger = context.getLogger();
        String response = new String("Hello world");
        Region region = Region.US_EAST_1;
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIA5HTDJKELQQ564C7Q",
                "w9IPZEn2voumZjc1cytZWGECSbGBMctwrN2fa7iM");
        CloudFormationClient cfClient = CloudFormationClient.builder()
                .region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        List<Stack> stacks = cfClient.describeStacks().stacks();
        for(Stack stack : stacks){
            logger.log("STACK Details: " + stack.stackName());
            logger.log("STACK Details: " + stack.stackStatus());
        }

        //cloud watch logs
        CloudWatchLogsClient cloudWatchLogsClient = CloudWatchLogsClient.builder().region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
        DescribeLogGroupsResponse describeLogGroupsResponse = cloudWatchLogsClient.describeLogGroups();
        cloudWatchLogsClient.deleteLogGroup()
        List<LogGroup> logGroups = describeLogGroupsResponse.logGroups();
        for(LogGroup logGroup : logGroups){
            logGroup.
        }

        // log execution details
        // process event
        logger.log("EVENT TYPE: " + event.getClass().toString());
        return response;
    }
}

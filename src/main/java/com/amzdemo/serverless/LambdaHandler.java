package com.amzdemo.serverless;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.Stack;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.DeleteLogGroupRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogGroupsResponse;
import software.amazon.awssdk.services.cloudwatchlogs.model.LogGroup;

import java.util.List;
import java.util.Map;


// Handler value: example.Handler
public class LambdaHandler implements RequestHandler<ScheduledEvent, String> {
    @Override
    public String handleRequest(ScheduledEvent event, Context context)
    {
        LambdaLogger logger = context.getLogger();
        Region region = Region.US_EAST_1;

            logger.log("Detail type :  " + event.getDetailType());
            logger.log("Detail type :  " + event.getDetail());
            logger.log("Detail type :  " + event.getSource());
            if(event != null){
                List<String> resources = event.getResources();
                if(resources != null){
                    for(String resource : resources){
                        logger.log("Resource :  " + resource);
                    }
                }

                Map<String, Object> details = event.getDetail();
                if(details != null){
                    for(Map.Entry entry : details.entrySet()){
                        if(entry != null){
                            logger.log("Key :  " + entry.getKey());
                            logger.log("Value :  " + entry.getValue());

                        }

                    }
                }
            }

        //cloud watch logs
        CloudWatchLogsClient cloudWatchLogsClient = CloudWatchLogsClient.builder().region(region)
                .build();
        DescribeLogGroupsResponse describeLogGroupsResponse = cloudWatchLogsClient.describeLogGroups();
        List<LogGroup> logGroups = describeLogGroupsResponse.logGroups();
        for(LogGroup logGroup : logGroups){
            logger.log("Deleting log group " + logGroup.logGroupName());
            DeleteLogGroupRequest deleteLogGroupRequest = DeleteLogGroupRequest.builder().logGroupName(logGroup.logGroupName()).build();
            cloudWatchLogsClient.deleteLogGroup(deleteLogGroupRequest);
        }

        // log execution details
        logger.log("Cleaned up log groups: " + event.getDetailType());
        return "success";
    }
}

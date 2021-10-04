package com.amzdemo.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;


// Handler value: example.Handler
public class LambdaHandler implements RequestHandler<Map<String,String>, String>{
    @Override
    public String handleRequest(Map<String,String> event, Context context)
    {
        LambdaLogger logger = context.getLogger();
        String response = new String("Hello world");
        // log execution details
        // process event
        logger.log("EVENT TYPE: " + event.getClass().toString());
        return response;
    }
}

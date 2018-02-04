package com.example.demofunction;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.Map;

public class LambdaHandler extends SpringBootRequestHandler<Map<String, Object>, String> {
    @Override
    protected Object convertEvent(Map<String, Object> event) {
        return event.get("data");
    }
}

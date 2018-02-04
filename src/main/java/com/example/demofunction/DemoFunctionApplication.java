package com.example.demofunction;

import functions.Greeter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@FunctionScan
public class DemoFunctionApplication {

//    @Bean
//    public Greeter greeter() {
//        return new Greeter();
//    }

    public static void main(String[] args) {
        SpringApplication.run(DemoFunctionApplication.class, args);
    }
}

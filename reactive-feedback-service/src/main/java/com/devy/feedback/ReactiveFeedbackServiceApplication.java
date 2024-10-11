package com.devy.feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReactiveFeedbackServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveFeedbackServiceApplication.class, args);
    }
}
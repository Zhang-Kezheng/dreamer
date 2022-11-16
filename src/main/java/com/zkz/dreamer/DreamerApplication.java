package com.zkz.dreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDreamer
public class DreamerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamerApplication.class,args);
    }
}

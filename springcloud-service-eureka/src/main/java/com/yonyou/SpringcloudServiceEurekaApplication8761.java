package com.yonyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringcloudServiceEurekaApplication8761 {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudServiceEurekaApplication8761.class, args);
    }

}

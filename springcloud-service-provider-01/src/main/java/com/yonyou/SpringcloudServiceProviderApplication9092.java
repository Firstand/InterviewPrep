package com.yonyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zhangyu
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringcloudServiceProviderApplication9092 {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudServiceProviderApplication9092.class, args);
    }

}

package com.yonyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringcloudServiceProviderApplication9090 {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudServiceProviderApplication9090.class, args);
    }

}

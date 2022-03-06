package com.yonyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebController {
    private final RestTemplate restTemplate;

    @Autowired
    public WebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("web")
    private String web() {
        return restTemplate.getForEntity("https://springcloud-service-provider/hello", String.class).getBody();
    }
}

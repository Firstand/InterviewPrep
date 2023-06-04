package com.yonyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zhangyu18
 */
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

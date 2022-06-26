package com.yonyou.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 即时通讯处理
 */
@RestController
public class InstantMessagingProcessController {

    @RequestMapping(value = "privateChat", method = RequestMethod.POST)
    public void privateChat(JSONObject message){

    }
}

package com.yonyou.controller;

import com.yonyou.domain.*;
import com.yonyou.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@RestController
@Slf4j
public class WechatController {
    /**
     * 工具类
     */
    @Autowired
    private WechatUtils wechatUtils;

    /**
     * 微信公众号接口配置验证
     *
     * @return 验签信息
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public String checkSignature(String signature, String timestamp,
                                 String nonce, String echostr) {
        log.info("signature = {}", signature);
        log.info("timestamp = {}", timestamp);
        log.info("nonce = {}", nonce);
        log.info("echostr = {}", echostr);
        // 第一步：自然排序
        String[] tmp = {wechatUtils.getToken(), timestamp, nonce};
        Arrays.sort(tmp);
        // 第二步：sha1 加密
        String sourceStr = StringUtils.join(tmp);
        String localSignature = DigestUtils.sha1Hex(sourceStr);
        // 第三步：验证签名
        if (signature.equals(localSignature)) {
            return echostr;
        }
        return null;
    }

    /**
     * 接收用户消息
     *
     * @param receiveMsgBody 消息
     * @return 响应内容
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.POST, produces = {"application/xml; charset=UTF-8"})
    @ResponseBody
    public Object getUserMessage(@RequestBody ReceiveMsgBody receiveMsgBody) {
        log.info("接收到的消息：{}", receiveMsgBody);
        MsgType msgType = MsgType.getMsgType(receiveMsgBody.getMsgType());
        switch (Objects.requireNonNull(msgType)) {
            case text:
                log.info("接收到的消息类型为{}", MsgType.text.getMsgTypeDesc());
                ResponseMsgBody textMsg = new ResponseMsgBody();
                textMsg.setToUserName(receiveMsgBody.getFromUserName());
                textMsg.setFromUserName(receiveMsgBody.getToUserName());
                textMsg.setCreateTime(System.currentTimeMillis());
                textMsg.setMsgType(MsgType.text.getMsgType());
                textMsg.setContent(receiveMsgBody.getContent());
                return textMsg;
            case image:
                log.info("接收到的消息类型为{}", MsgType.image.getMsgTypeDesc());
                ResponseImageMsg imageMsg = new ResponseImageMsg();
                imageMsg.setToUserName(receiveMsgBody.getFromUserName());
                imageMsg.setFromUserName(receiveMsgBody.getToUserName());
                imageMsg.setCreateTime(System.currentTimeMillis());
                imageMsg.setMsgType(MsgType.image.getMsgType());
                imageMsg.setMediaId(new String[]{receiveMsgBody.getMediaId()});
                return imageMsg;
            case voice:
                log.info("接收到的消息类型为{}", MsgType.voice.getMsgTypeDesc());
                ResponseVoiceMsg voiceMsg = new ResponseVoiceMsg();
                voiceMsg.setToUserName(receiveMsgBody.getFromUserName());
                voiceMsg.setFromUserName(receiveMsgBody.getToUserName());
                voiceMsg.setCreateTime(System.currentTimeMillis());
                voiceMsg.setMsgType(MsgType.voice.getMsgType());
                voiceMsg.setMediaId(new String[]{receiveMsgBody.getMediaId()});
                return voiceMsg;
            default:
                // 其他类型
                break;
        }
        return null;
    }

    @RequestMapping("/getAccessToken")
    public void getAccessToken() {
        try {
            String accessToken = wechatUtils.getAccessToken();
            log.info("access_token = {}", accessToken);
        } catch (WxErrorException e) {
            log.error("获取access_token失败。", e);
        }
    }
}
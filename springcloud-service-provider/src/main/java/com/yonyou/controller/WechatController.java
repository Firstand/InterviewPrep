package com.yonyou.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yonyou.domain.*;
import com.yonyou.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.XmlUtils;
import me.chanjar.weixin.mp.bean.message.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/woa")
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
    @RequestMapping(value = "wechat", method = RequestMethod.GET)
    public String checkSignature(String signature, String timestamp,
                                 String nonce, String echostr) {
        if(StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)){
            return null;
        }
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
     * @param wxMpXmlMessage 消息
     * @return 响应内容
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.POST, produces = {"application/xml; charset=UTF-8"})
    @ResponseBody
    public Object getUserMessage(@RequestBody WxMpXmlMessage wxMpXmlMessage) throws JsonProcessingException {
        log.info("message 接收到的消息：{}", wxMpXmlMessage);
        MsgType msgType = MsgType.getMsgType(wxMpXmlMessage.getMsgType());
        log.info("message 接收到的消息类型为{}", msgType.getMsgTypeDesc());
        WxMpXmlOutMessage wxMpXmlOutMessage;
        switch (msgType.getMsgType()) {
            case WxConsts.XmlMsgType.TEXT:
                wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                        .content(wxMpXmlMessage.getContent())
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser())
                        .build();
                break;
            case WxConsts.XmlMsgType.VOICE:
                wxMpXmlOutMessage =  WxMpXmlOutMessage.VOICE()
                        .mediaId(wxMpXmlMessage.getMediaId())
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser())
                        .build();
                break;
            case WxConsts.XmlMsgType.VIDEO:
                wxMpXmlOutMessage = WxMpXmlOutMessage.VIDEO()
                        .mediaId(wxMpXmlMessage.getMediaId())
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser())
                        .title(wxMpXmlMessage.getTitle())
                        .description(wxMpXmlMessage.getDescription())
                        .build();
                break;
            case WxConsts.XmlMsgType.IMAGE:
                wxMpXmlOutMessage = WxMpXmlOutMessage.IMAGE()
                        .mediaId(wxMpXmlMessage.getMediaId())
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser())
                        .build();
                break;
            default:
                wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                        .content("你发的啥消息呀，没整明白![Facepalm]")
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser())
                        .build();
                break;
        }
        log.info("message 回复的消息：{}", wxMpXmlOutMessage);
        log.info("message 回复的消息类型为{}", MsgType.getMsgType(wxMpXmlMessage.getMsgType()).getMsgTypeDesc());
        return wxMpXmlOutMessage;
    }

    @RequestMapping("getAccessToken")
    public void getAccessToken() {
        try {
            String accessToken = wechatUtils.getAccessToken();
            log.info("access_token = {}", accessToken);
        } catch (WxErrorException e) {
            log.error("获取access_token失败。", e);
        }
    }
}
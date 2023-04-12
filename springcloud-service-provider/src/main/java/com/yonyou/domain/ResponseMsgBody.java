package com.yonyou.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 响应消息体
 *
 * @author zhangyu18
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResponseMsgBody {
    /**
     * 接收方帐号（收到的OpenID）
     */
    private String toUserName;
    /**
     * 开发者微信号
     */
    private String fromUserName;
    /**
     * 消息创建时间
     */
    private long createTime;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 文本消息的消息体
     */
    private String content;
}
package com.yonyou.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 接收消息实体
 *
 * @author zhangyu18
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ReceiveMsgBody {
    /**
     * 开发者微信号
     */
    private String toUserName;
    /**
     * 发送消息用户的openId
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
     * 消息ID，根据该字段来判重处理
     */
    private long msgId;
    /**
     * 文本消息的消息体
     */
    private String content;
    /**
     * 图片媒体ID
     */
    private String mediaId;
}
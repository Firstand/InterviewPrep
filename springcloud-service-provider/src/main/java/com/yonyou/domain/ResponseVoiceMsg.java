package com.yonyou.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 语音消息响应实体类
 *
 * @author zhangyu18
 */
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResponseVoiceMsg extends ResponseMsgBody {
    /**
     * 图片媒体ID
     */
    @XmlElementWrapper(name = "Voice")
    private String[] mediaId;
}
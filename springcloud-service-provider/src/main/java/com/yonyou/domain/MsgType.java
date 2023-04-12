package com.yonyou.domain;


/**
 * 消息类型枚举
 *
 * @author zhangyu18
 */
public enum MsgType {
    /**
     * 各种消息类型
     */
    text("text", "文本消息"),
    image("image", "图片消息"),
    voice("voice", "语音消息"),
    video("video", "视频消息"),
    shortvideo("shortvideo", "小视频消息"),
    location("location", "地理位置消息"),
    link("link", "链接消息"),
    music("music", "音乐消息"),
    news("news", "图文消息");

    MsgType(String msgType, String msgTypeDesc) {
        this.msgType = msgType;
        this.msgTypeDesc = msgTypeDesc;
    }

    private String msgType;
    private String msgTypeDesc;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTypeDesc() {
        return msgTypeDesc;
    }

    public void setMsgTypeDesc(String msgTypeDesc) {
        this.msgTypeDesc = msgTypeDesc;
    }

    /**
     * 获取对应的消息类型
     *
     * @param msgType 消息类型
     * @return 返回枚举值
     */
    public static MsgType getMsgType(String msgType) {
        switch (msgType) {
            case "text":
                return text;
            case "image":
                return image;
            case "voice":
                return voice;
            case "video":
                return video;
            case "shortvideo":
                return shortvideo;
            case "location":
                return location;
            case "link":
                return link;
            case "music":
                return music;
            case "news":
                return news;
            default:
                return null;
        }
    }
}
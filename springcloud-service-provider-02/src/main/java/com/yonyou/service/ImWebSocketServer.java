package com.yonyou.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @Desc : 服务端服务类
 * @author : Chenweixian 陈惟鲜
 * @Date : 2021年2月25日 下午4:33:21
 */
@ServerEndpoint("/imserver/{userId}")
@Component
@Slf4j
public class ImWebSocketServer {

    /** concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。 */
    private static ConcurrentHashMap<String, ImWebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;
    /** 接收userId */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketMap.remove(userId);
        webSocketMap.put(userId, this);

        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(userId);
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        // 可以群发消息
        // 消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                // 解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                // 追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                // 传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    log.error("请求的userId:" + toUserId + "不在该服务器上");
                    // 否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                log.error("异常", e);
            }
        }
    }

    /**
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /** 在线人数 */
    public static synchronized int getOnlineCount() {
        return webSocketMap.size();
    }
    
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }
    
    /**
     * 发送自定义消息--群发
     */
    public static void sendInfoGroup(String message) throws IOException {
        if(ImWebSocketServer.webSocketMap != null && ImWebSocketServer.webSocketMap.size() > 0) {
            for (String userId: ImWebSocketServer.webSocketMap.keySet()) {
                sendInfo(message, userId);
            }
        }
    }
}
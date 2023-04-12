package com.yonyou.utils;

import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 微信工具类
 *
 * @author zhangyu18
 */
@Component
@Data
public class WechatUtils {

    @Value("${wechat.interface.config.token}")
    private String token;
    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.appsecret}")
    private String appsecret;

    /**
     * 调用微信接口
     */
    private WxMpService wxMpService;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(appid);
        wxMpConfigStorage.setSecret(appsecret);
        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
    }

    /**
     * 获取 access_token 不刷新
     *
     * @return access_token
     * @throws WxErrorException 微信异常
     */
    public String getAccessToken() throws WxErrorException {
        return wxMpService.getAccessToken();
    }

}
package com.github.tomseanmy.tushansusu.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 通知配置
 * @author tomsean
 */
@ConfigurationProperties(prefix = "notice")
public class NoticeProperty {

    /**
     * 企业微信，项目code: 机器人key
     */
    private Map<String, String> wxwork;

    public Map<String, String> getWxwork() {
        return wxwork;
    }

    public void setWxwork(Map<String, String> wxwork) {
        this.wxwork = wxwork;
    }
}

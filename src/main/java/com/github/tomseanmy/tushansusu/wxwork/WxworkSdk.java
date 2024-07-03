package com.github.tomseanmy.tushansusu.wxwork;

import com.alibaba.fastjson2.JSON;
import com.github.tomseanmy.tushansusu.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author tomsean
 */
public class WxworkSdk {

    public static final String HOST = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send";
    private static final Logger log = LoggerFactory.getLogger(WxworkSdk.class);

    private final Map<String, String> keyMap;
    public WxworkSdk(Map<String, String> keyMap) {
        this.keyMap = keyMap;
    }

    private String getUrl(String code) {
        if (!keyMap.containsKey(code)) {
            throw new RuntimeException("Not found project code: " + code);
        }
        String key = keyMap.get(code);
        return HOST.concat("?key=" + key);
    }

    public static WxworkSdk create(Map<String, String> keyMap) {
        return new WxworkSdk(keyMap);
    }

    public void send(String code, Req req) {
        if (!keyMap.containsKey(code)) {
            return;
        }
        String key = keyMap.get(code);
        HttpUtil.postJson(getUrl(code), JSON.toJSONString(req), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("send wxwork failed", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("send wxwork succcess {}", response.body().string());
            }
        });
    }
}

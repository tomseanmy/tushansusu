package com.github.tomseanmy.tushansusu.handle;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author tomsean
 */
public interface EventHandler {

    String objectKind();

    void trigger(JSONObject event);
}

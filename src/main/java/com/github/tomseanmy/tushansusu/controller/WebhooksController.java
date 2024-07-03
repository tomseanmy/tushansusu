package com.github.tomseanmy.tushansusu.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.tomseanmy.tushansusu.Context;
import com.github.tomseanmy.tushansusu.handle.EventFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author tomsean
 */
@RestController
public class WebhooksController {

    private final static Logger log = LoggerFactory.getLogger(WebhooksController.class);

    /**
     * gitlab回调
     * @param request
     * @return
     */
    @PostMapping("/webhooks/{code}")
    public String webhooks(@PathVariable String code, HttpServletRequest request) {
        JSONObject event = getBody(request);
        if (Objects.isNull(event)) {
            log.error("not found body");
            return "error";
        }
        Context.setPrjCode(code);
        EventFactory.dispatch(event);
        return "ok";
    }

    public JSONObject getBody(HttpServletRequest request) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            String temp;
            while (StringUtils.hasText(temp = reader.readLine())) {
                builder.append(temp);
            }
            return JSONObject.parseObject(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

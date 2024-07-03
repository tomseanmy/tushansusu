package com.github.tomseanmy.tushansusu.handle;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * gitlab事件管理
 * @author tomsean
 */
@Component
public class EventFactory implements ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(EventFactory.class);

    public static final int INIT_SIZE = 15;
    private final static Map<String, EventHandler> HANDLER_MAP = new HashMap<>(INIT_SIZE);

    /**
     * 分发事件
     * @param event
     */
    public static void dispatch(JSONObject event) {
        String objectKind = event.getString("object_kind");
        if (!HANDLER_MAP.containsKey(objectKind)) {
            String message = "Unsupported event object_kind, object_kind=" + objectKind;
            log.error(message);
            throw new RuntimeException(message);
        }
        HANDLER_MAP.get(objectKind)
                .trigger(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(EventHandler.class)
                .values().forEach(h -> HANDLER_MAP.put(h.objectKind(), h));
    }
}

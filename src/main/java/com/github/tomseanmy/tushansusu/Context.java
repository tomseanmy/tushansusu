package com.github.tomseanmy.tushansusu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 上下文
 */
public class Context {
    private static final ThreadLocal<Map<String, Object>> CONTEXT = new InheritableThreadLocal<>();
    public static final int INIT_SIZE = 30;

    /**
     * 创建上下文
     */
    public static void create() {
        CONTEXT.set(new HashMap<>(INIT_SIZE));
    }

    /**
     * 创建上下文并规定上下文初始长度
     * @param initSize 初始长度
     */
    public static void create(int initSize) {
        CONTEXT.set(new HashMap<>(initSize));
    }

    /**
     * 销毁全部上下文内容
     */
    public static void destroy() {
        CONTEXT.remove();
    }

    private static Map<String, Object> getMap() {
        Map<String, Object> map = CONTEXT.get();
        if (map == null) {
            throw new RuntimeException("content is null");
        }
        return map;
    }

    /**
     * 通过类型获取上下文值
     * @param clazz 值类型
     * @return 值
     * @param <T> 值类型
     */
    public static <T> Optional<T> get(Class<?> clazz) {
        return get(clazz.getName());
    }

    /**
     * 通过Key值获取上下文值
     * @param key 键
     * @return 值
     * @param <T> 值类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> get(String key) {
        return Optional.ofNullable(getMap().get(key))
                .map(o -> (T) o);
    }

    /**
     * 存入值到上下文
     * @param key 键
     * @param val 值
     */
    public static void set(String key, Object val) {
        if (val == null) {
            return;
        }
        getMap().put(key, val);
    }

    /**
     * 存入值到上下文（使用值类型作为键）
     * @param val 值
     */
    public static void set(Object val) {
        if (val == null) {
            return;
        }
        getMap().put(val.getClass().getName(), val);
    }

    public static String getPrjCode() {
        return getMap().getOrDefault(Constant.PRJ_CODE, "").toString();
    }

    public static void setPrjCode(String prjCode) {
        getMap().put(Constant.PRJ_CODE, prjCode);
    }

}

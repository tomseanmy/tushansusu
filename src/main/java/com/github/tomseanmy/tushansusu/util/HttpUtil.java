package com.github.tomseanmy.tushansusu.util;
import okhttp3.*;

import java.util.Map;
import java.util.Optional;

public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient();
    public static final String JSON_MEDIA = "application/json; charset=utf-8";

    /**
     * 发送带请求头的 GET 请求
     * @param url
     * @param headers
     * @param callback
     */
    public static void get(String url, Map<String, String> headers, Callback callback) {
        Request.Builder reqBuilder = new Request.Builder()
                .url(url);
        Optional.ofNullable(headers)
                .ifPresent(p -> p.forEach(reqBuilder::addHeader));
        Call call = client.newCall(reqBuilder.build());
        call.enqueue(callback);
    }

    /**
     * 发送带请求头和请求参数的 POST 请求
     * @param url
     * @param params
     * @param headers
     * @param callback
     */
    public static void postForm(String url, Map<String, String> params, Map<String, String> headers, Callback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        Optional.ofNullable(params)
                .ifPresent(p -> p.forEach(formBuilder::addEncoded));
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(formBuilder.build());
        Optional.ofNullable(headers)
                .ifPresent(h -> h.forEach(reqBuilder::addHeader));
        Call call = client.newCall(reqBuilder.build());
        call.enqueue(callback);
    }

    /**
     * 发送 POST JSON 请求
     * @param url
     * @param json
     * @param callback
     */
    public static void postJson(String url, String json, Callback callback) {
        MediaType JSON = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
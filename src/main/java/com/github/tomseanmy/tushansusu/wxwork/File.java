package com.github.tomseanmy.tushansusu.wxwork;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author tomsean
 */
public class File {

    @JSONField(name = "media_id")
    private String mediaId;

    public File() {
    }

    public File(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}

package com.github.tomseanmy.tushansusu.wxwork;

/**
 * @author tomsean
 */
public class Image {

    private String base64;
    private String md5;

    public Image() {
    }

    public Image(String base64, String md5) {
        this.base64 = base64;
        this.md5 = md5;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}

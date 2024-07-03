package com.github.tomseanmy.tushansusu.wxwork;

import java.util.List;

/**
 * @author tomsean
 */
public class Req {

    public interface MsgType {
        String TEXT = "text";
        String MD = "markdown";
        String IMAGE = "image";
        String FILE = "file";
    }

    public static class Builder {
        public static Req text(String text) {
            Req req = new Req();
            req.setMsgtype(MsgType.TEXT);
            req.setText(new Text(text));
            return req;
        }
        public static Req text(String text, List<String> mentionedList) {
            Req req = new Req();
            req.setMsgtype(MsgType.TEXT);
            req.setText(new Text(text, mentionedList));
            return req;
        }

        public static Req text(String text, List<String> mentionedList, List<String> mentionedMobileList) {
            Req req = new Req();
            req.setMsgtype(MsgType.TEXT);
            req.setText(new Text(text, mentionedList, mentionedMobileList));
            return req;
        }

        public static Req md(String mdContent) {
            Req req = new Req();
            req.setMsgtype(MsgType.MD);
            req.setMarkdown(new Markdown(mdContent));
            return req;
        }
    }

    private String msgtype = "text";

    private Text text;

    private Image image;

    private Markdown markdown;

    private File file;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

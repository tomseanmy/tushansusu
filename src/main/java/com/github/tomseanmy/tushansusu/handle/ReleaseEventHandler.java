package com.github.tomseanmy.tushansusu.handle;

import com.alibaba.fastjson2.JSONObject;
import com.github.tomseanmy.tushansusu.Context;
import com.github.tomseanmy.tushansusu.wxwork.Req;
import com.github.tomseanmy.tushansusu.wxwork.WxworkSdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tomsean
 */
@Component
public class ReleaseEventHandler implements EventHandler {

    private WxworkSdk wxworkSdk;

    @Override
    public String objectKind() {
        return "release";
    }

    @Override
    public void trigger(JSONObject event) {
        String markdown = buildTitle(event)
                + "\n"
                + buildAttach(event);
        wxworkSdk.send(Context.getPrjCode(), Req.Builder.md(markdown));
    }

    public String buildTitle(JSONObject event) {
        JSONObject project = event.getJSONObject("project");
        String titleTemp = "## [[$project]($project_url)] 发布新版本 [$release_name]($release_url) \n > <font color=\"comment\">$desc</font>";
        String urlPref = project.getString("web_url");
        String releaseUrl = event.getString("url");
        return titleTemp
                .replace("$project_url", urlPref)
                .replace("$project", event.getString("name"))
                .replace("$release_name", event.getString("name"))
                .replace("$release_url", releaseUrl)
                .replace("$desc", event.getString("description"))
                ;
    }

    public String buildAttach(JSONObject event) {
        String attachTemp = "> [$name]($url) \n";
        StringBuilder attachBuilder = new StringBuilder();
        event.getJSONObject("assets").getJSONArray("sources").forEach(
                item -> {
                    JSONObject link = (JSONObject)item;
                    String url = link.getString("url");
                    String[] urlSplit = url.split("/");
                    String name = urlSplit[urlSplit.length - 1];
                    attachBuilder.append(attachTemp.replace("$name", name)
                            .replace("$url", url));
                });
        return attachBuilder.toString();
    }

    @Autowired
    public void setWxworkSdk(WxworkSdk wxworkSdk) {
        this.wxworkSdk = wxworkSdk;
    }
}

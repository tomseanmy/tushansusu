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
public class MergeRequestHandler implements EventHandler {

    private WxworkSdk wxworkSdk;

    @Override
    public String objectKind() {
        return "merge_request";
    }

    @Override
    public void trigger(JSONObject event) {
        String markdown = buildTitle(event)
                + "\n\n"
                + buildCommit(event);
        wxworkSdk.send(Context.getPrjCode(), Req.Builder.md(markdown));
    }

    public String buildTitle(JSONObject event) {
        String titleTemp = "## [[$project_name]($project_url)] 请求合并[$req_name]($req_url) $source to $target \n > **发起人：** $user \n > **说明：** $desc";
        JSONObject project = event.getJSONObject("project");
        JSONObject attributes = event.getJSONObject("object_attributes");
        return titleTemp
                .replace("$source", attributes.getString("source_branch"))
                .replace("$target", attributes.getString("target_branch"))
                .replace("$desc", attributes.getString("description"))
                .replace("$req_name", attributes.getString("title"))
                .replace("$req_url", attributes.getString("url"))
                .replace("$user", event.getJSONObject("user").getString("name"))
                .replace("$project_name", project.getString("name"))
                .replace("$project_url", project.getString("web_url"));
    }

    public String buildCommit(JSONObject event) {
        JSONObject project = event.getJSONObject("project");
        String commitTemp = "> [$id_short]($url/-/commit/$id)(<font color=\"comment\">$user</font>): $msg ";
        String urlPerf = project.getString("web_url");
        JSONObject attributes = event.getJSONObject("object_attributes");
        JSONObject commit = attributes.getJSONObject("last_commit");
        return commitTemp.replace("$id_short", commit.getString("id").substring(0, 7))
                .replace("$id", commit.getString("id"))
                .replace("$url", urlPerf)
                .replace("$msg", commit.getString("message"))
                .replace("$user", event.getJSONObject("user").getString("name"));
    }

    @Autowired
    public void setWxworkSdk(WxworkSdk wxworkSdk) {
        this.wxworkSdk = wxworkSdk;
    }
}

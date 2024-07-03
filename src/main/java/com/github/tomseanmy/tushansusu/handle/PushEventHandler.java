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
public class PushEventHandler implements EventHandler {

    private WxworkSdk wxworkSdk;

    @Override
    public String objectKind() {
        return "push";
    }

    @Override
    public void trigger(JSONObject event) {
        String markdown = buildTitle(event)
                + "\n\n"
                + buildCommit(event);
        wxworkSdk.send(Context.getPrjCode(), Req.Builder.md(markdown));
    }


    public String buildTitle(JSONObject event) {
        JSONObject project = event.getJSONObject("project");
        String urlPref = project.getString("web_url");
        String branchTemp = "## [[$project]($url)] 推送分支 [$branch]($url/-/tree/$branch)\n > **发起人：** $user ";
        String branch = event.getString("ref");
        return branchTemp.replace("$user", event.getString("user_name"))
                .replace("$branch", branch)
                .replace("$project", project.getString("name"))
                .replace("$url", urlPref);
    }

    public String buildCommit(JSONObject event) {
        JSONObject project = event.getJSONObject("project");
        String urlPref = project.getString("web_url");
        String commitTemp = "> [$id_short]($url/-/commit/$id)(<font color=\"comment\">$author</font>): $msg ";
        StringBuilder commitBuilder = new StringBuilder();
        event.getJSONArray("commits").forEach(item -> {
                JSONObject commit = (JSONObject) item;
                commitBuilder.append(
                        commitTemp.replace("$id_short", commit.getString("id").substring(0, 7))
                                .replace("$id",commit.getString("id"))
                                .replace("$url", urlPref)
                                .replace("$msg", commit.getString("message"))
                                .replace("$author", commit.getJSONObject("author").getString("name")));
                }
        );
        return commitBuilder.toString();
    }

    @Autowired
    public void setWxworkSdk(WxworkSdk wxworkSdk) {
        this.wxworkSdk = wxworkSdk;
    }
}

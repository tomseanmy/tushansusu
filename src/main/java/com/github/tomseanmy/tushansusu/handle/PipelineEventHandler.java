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
public class PipelineEventHandler implements EventHandler {

    private WxworkSdk wxworkSdk;

    @Override
    public String objectKind() {
        return "pipeline";
    }

    @Override
    public void trigger(JSONObject event) {
        String markdown = buildTitle(event)
                + "\n"
                + buildJobs(event);
        wxworkSdk.send(Context.getPrjCode(), Req.Builder.md(markdown));
    }

    public String buildTitle(JSONObject event) {
        String titleTemp = "## [[$project_name]($project_url)] [流水线#$pipeline]($url/-/pipelines/$pipeline) \n > $cur/$total **$status** \n ";
        JSONObject project = event.getJSONObject("project");
        JSONObject attribute = event.getJSONObject("object_attributes");
        String urlPref = project.getString("web_url");
        String pipelineId = attribute.getString("id");
        return titleTemp.replace("$pipeline", pipelineId)
                .replace("$url", urlPref)
                .replace("$cur", String.valueOf(event.getJSONArray("builds").stream().filter(item -> {
                    JSONObject j = (JSONObject) item;
                    return !j.getString("status").equals("created");
                }).count()))
                .replace("$total", String.valueOf(event.getJSONArray("builds").size()))
                .replace("$status", attribute.getString("status"))
                .replace("$project_name", project.getString("name"))
                .replace("$project_url", project.getString("web_url"));
    }

    public String buildJobs(JSONObject event) {
        String jobsTemp = "> [$id_short]($url/-/jobs/$id) `$stage` $name $status \n";
        JSONObject project = event.getJSONObject("project");
        String urlPref = project.getString("web_url");
        StringBuilder jobsBuilder = new StringBuilder();
        event.getJSONArray("builds").forEach(job -> {
            JSONObject j = (JSONObject) job;
            jobsBuilder.append(
                    jobsTemp.replace("$id_short", j.getString("id"))
                            .replace("$id", String.valueOf(j.getString("id")))
                            .replace("$url", urlPref)
                            .replace("$name", j.getString("name"))
                            .replace("$stage", j.getString("stage"))
                            .replace("$status", buildState(j.getString("status")))
            );
        });
        return jobsBuilder.toString();
    }

    public String buildState(String jobStatus) {
        if ("CREATED".equalsIgnoreCase(jobStatus)) {
            return "<font>就绪</font>";
        }
        if ("RUNNING".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"comment\">执行中</font>";
        }
        if ("FAILED".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"warning\">失败</font>";
        }
        if ("SUCCESS".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"info\">成功</font>";
        }
        if ("SKIPPED".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"comment\">跳过</font>";
        }
        if ("CANCELED".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"comment\">取消</font>";
        }
        if ("PENDING".equalsIgnoreCase(jobStatus)) {
            return "<font color=\"comment\">排队</font>";
        }
        return "";
    }

    @Autowired
    public void setWxworkSdk(WxworkSdk wxworkSdk) {
        this.wxworkSdk = wxworkSdk;
    }
}

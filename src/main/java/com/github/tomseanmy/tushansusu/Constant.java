package com.github.tomseanmy.tushansusu;

import java.util.Arrays;

/**
 * @author tomsean
 */
public interface Constant {
    /**
     * 推送项目code
     */
    String PRJ_CODE = "prj_code";
    String HEAD_GITLAB_TOKEN = "X-Gitlab-Token";
    String HEAD_GITLAB_EVENT = "X-Gitlab-Event";

    enum GitlabEvent {
        ISSUE_HOOK("Issue Hook"),
        JOB_HOOK("Job Hook"),
        MERGE_REQUEST_HOOK("Merge Request Hook"),
        NOTE_HOOK("Note Hook"),
        PIPELINE_HOOK("Pipeline Hook"),
        PUSH_HOOK("Push Hook"),
        TAG_PUSH_HOOK("Tag Push Hook"),
        WIKI_PAGE_HOOK("Wiki Page Hook"),
        DEPLOYMENT_HOOK("Deployment Hook"),
        RELEASE_HOOK("Release Hook"),
        ;
        private final String value;

        GitlabEvent(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static GitlabEvent parse(String value) {
            return Arrays.stream(values()).filter(e -> e.value.equals(value)).findAny()
                    .orElseThrow(() -> new RuntimeException("Unsupported Gitlab event: " + value));
        }
    }
}

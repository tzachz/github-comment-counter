package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 * A very partial representation of a GitHub Pull Request Comment
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHComment {

    private final GHUser user;
    private final String pullRequestUrl;
    private final String body;

    @JsonCreator
    public GHComment(@JsonProperty("user") GHUser user,
                     @JsonProperty("pull_request_url") String pullRequestUrl,
                     @JsonProperty("body") String body) {
        this.body = body;
        this.user = user;
        this.pullRequestUrl = pullRequestUrl;
    }

    public GHComment(String user, String pullRequestUrl, String body) {
        this.user = new GHUser(user);
        this.pullRequestUrl = pullRequestUrl;
        this.body = body;
    }

    public GHUser getUser() {
        return user;
    }

    public String getPullRequestUrl() {
        return pullRequestUrl;
    }

    public String getBody() {
        return body;
    }
}

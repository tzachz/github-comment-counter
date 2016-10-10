package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 * A very partial representation of a GitHub Pull Request Comment
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHComment {

    private GHUser user;
    private String pullRequestUrl;
    private String body;
    private String htmlUrl;
    private Date createDate;

    public GHUser getUser() {
        return user;
    }

    @JsonProperty("user")
    public GHComment setUser(GHUser user) {
        this.user = user;
        return this;
    }

    public String getPullRequestUrl() {
        return pullRequestUrl;
    }

    @JsonProperty("pull_request_url")
    public GHComment setPullRequestUrl(String pullRequestUrl) {
        this.pullRequestUrl = pullRequestUrl;
        return this;
    }

    /**
     * Sets the pull request URL based on the given issue URL (for
     * compatibility reasons for issue comments on pull requests).
     *
     * @param issueUrl API URL for issue the comment belongs to
     * @return this
     */
    @JsonProperty("issue_url")
    public GHComment setIssueUrl(String issueUrl) {
        // This is hacky, but GitHub's Issue Comments API doesn't provide
        // something to link back to the pull request.
        String[] components = issueUrl.split("/");
        components[6] = "pulls";
        return setPullRequestUrl(Joiner.on('/').join(components));
    }

    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public GHComment setBody(String body) {
        this.body = body;
        return this;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public GHComment setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @JsonProperty("created_at")
    public GHComment setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}

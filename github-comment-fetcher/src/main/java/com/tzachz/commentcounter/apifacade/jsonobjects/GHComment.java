package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 * A very partial representation of a GitHub Pull Request Comment
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHComment {

    private GHUser user;
    private String pull_request_url;

    @SuppressWarnings("UnusedDeclaration")
    public GHComment() {
    }

    public GHComment(String user, String pull_request_url) {
        this.user = new GHUser(user);
        this.pull_request_url = pull_request_url;
    }

    public GHUser getUser() {
        return user;
    }

    public String getPull_request_url() {
        return pull_request_url;
    }
}

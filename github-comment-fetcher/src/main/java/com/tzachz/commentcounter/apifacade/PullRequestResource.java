package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;

public class PullRequestResource extends GitHubResource {

    protected PullRequestResource(String username, String password) {
        super(username, password);
    }

    protected PullRequestResource(String token) {
        super(token);
    }

    public GHPullRequest getPullRequest(String url) {
        return get(url, GHPullRequest.class);
    }
}

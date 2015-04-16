package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;

public class PullRequestResource extends GitHubResource {

    public PullRequestResource(Credentials credentials) {
        super(credentials);
    }

    public GHPullRequest getPullRequest(String url) {
        return get(url, GHPullRequest.class);
    }
}

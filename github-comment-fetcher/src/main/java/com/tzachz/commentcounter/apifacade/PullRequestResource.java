package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;

import com.sun.jersey.api.client.UniformInterfaceException;

public class PullRequestResource extends GitHubResource {

    public PullRequestResource(Credentials credentials) {
        super(credentials);
    }

    public GHPullRequest getPullRequest(String url) {
        try {
            return get(url, GHPullRequest.class);
        } catch (UniformInterfaceException uie) {
            if (uie.getResponse().getStatus() == 404) {
                return null;
            } else {
                throw uie;
            }
        }
    }
}

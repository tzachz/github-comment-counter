package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

class PullRequestResource extends GitHubResource {

    private static final DateFormat SINCE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    PullRequestResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    GHPullRequest getPullRequest(String url) {
        return get(url, GHPullRequest.class);
    }

    Collection<GHPullRequest> getPullRequests(String orgName, String repoName, Date since) {
        final Collection<GHPullRequest> prs = new ArrayList<>();
        final WebResource resource = getResource()
                .path("repos")
                .path(orgName)
                .path(repoName)
                .path("pulls")
                .queryParam("since", SINCE_FORMAT.format(since));
        scanPages(resource, new GenericType<List<GHPullRequest>>() {}, prs::addAll);
        return prs;
    }
}

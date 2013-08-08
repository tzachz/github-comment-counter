package com.tzachz.github.commentcounter;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.github.commentcounter.api.GHEvent;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class GitHubCommentCounter extends GitHubClient {

    private static final int MAX_PAGES = 10; // limited by GH :(

    public GitHubCommentCounter(String username, String password) {
        super(username, password);
    }

    public UserComments getUserComments(String organization, String repoName) {
        final UserComments userComments = new UserComments();
        WebResource resource = getResource().path("repos").path(organization).path(repoName).path("events");
        scanPages(resource, MAX_PAGES, new GenericType<List<GHEvent>>() {}, new PageProcessor<GHEvent>() {
            @Override
            public void process(List<GHEvent> page) {
                userComments.addAll(page);
            }
        });
        return userComments;
    }

}

